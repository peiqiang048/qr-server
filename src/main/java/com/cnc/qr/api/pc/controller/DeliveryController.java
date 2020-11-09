package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.GetDeliverySettingInputResource;
import com.cnc.qr.api.pc.resource.GetDeliverySettingOutputResource;
import com.cnc.qr.api.pc.resource.GetSelectedAreaListInputResource;
import com.cnc.qr.api.pc.resource.GetSelectedAreaListOutputResource;
import com.cnc.qr.api.pc.resource.RegistDelivertSettingInputResource;
import com.cnc.qr.api.pc.resource.RegistDelivertSettingOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.DeliveryTypeWay;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.AreaListDto;
import com.cnc.qr.core.pc.model.BlockListDto;
import com.cnc.qr.core.pc.model.CateringPriceTypeListDto;
import com.cnc.qr.core.pc.model.CityListDto;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingInputDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingOutputDto;
import com.cnc.qr.core.pc.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.pc.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.pc.model.PrefectureListDto;
import com.cnc.qr.core.pc.model.RegistDelivertSettingInputDto;
import com.cnc.qr.core.pc.service.AreaInfoService;
import com.cnc.qr.core.pc.service.DeliveryService;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出前情報コントローラ.
 */
@RestController
public class DeliveryController {

    /**
     * 出前設定取得サービス.
     */
    @Autowired
    private DeliveryService deliveryService;

    /**
     * エリアサービス.
     */
    @Autowired
    private AreaInfoService areaService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 出前設定取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 出前設定情報
     */
    @PostMapping(UrlConstants.PC_GET_DELIVERY_SETTING)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetDeliverySettingOutputResource getDeliverySetting(
        @Validated @RequestBody GetDeliverySettingInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliverySettingInputDto inputDto = beanMapper
            .map(inputResource, GetDeliverySettingInputDto.class);

        // サービス処理を実行する
        GetDeliverySettingOutputDto outputDto = deliveryService.getDeliverySetting(inputDto);

        // インプット情報をDTOにセットする
        GetDeliverySettingOutputResource outputResource = new GetDeliverySettingOutputResource();

        beanMapper.map(outputDto, outputResource);

        // 都道府県リスト取得
        List<PrefectureListDto> prefectureList = deliveryService.getPrefectureList(inputDto);
        outputResource.setPrefectureList(prefectureList);

        // エリアリスト取得
        List<AreaListDto> areaList = deliveryService.getAreaList(inputDto);
        outputResource.setAreaList(areaList);
        outputResource.setCityList(new ArrayList<>());
        outputResource.setBlockList(new ArrayList<>());

        // 市区町村リスト取得
        if (CollectionUtils.isNotEmpty(prefectureList)) {
            inputDto.setPrefectureId(prefectureList.get(0).getPrefectureId());
            List<CityListDto> cityList = deliveryService.getCityList(inputDto);
            outputResource.setCityList(cityList);
            // 町域番地リスト取得
            if (CollectionUtils.isNotEmpty(cityList)) {
                inputDto.setCityId(cityList.get(0).getCityId());
                List<BlockListDto> blockList = deliveryService.getBlockList(inputDto);
                // 町域番地リスト設定
                for (AreaListDto areaDto : areaList) {
                    for (BlockListDto blockDto : blockList) {
                        if (!Objects
                            .equals(Flag.ON.getCode().toString(), blockDto.getCheckFlag())) {
                            if (Objects.equals(blockDto.getBlockId(), areaDto.getBlockId())
                                && Objects.equals(areaDto.getCityId(), inputDto.getCityId())
                                && Objects
                                .equals(areaDto.getPrefectureId(), inputDto.getPrefectureId())) {
                                blockDto.setCheckFlag(Flag.ON.getCode().toString());
                            } else {
                                blockDto.setCheckFlag(Flag.OFF.getCode().toString());
                            }
                        }
                    }
                }
                outputResource.setBlockList(blockList);
            }
        }

        // 出前区分リスト取得
        List<DeliveryFlagListDto> deliveryFlagList = deliveryService.getDeliveryFlagList(inputDto);
        outputResource.setDeliveryFlagList(deliveryFlagList);

        // 出前仕方リスト取得
        List<DeliveryTypeFlagListDto> deliveryTypeFlagList = deliveryService
            .getDeliveryTypeFlagList(inputDto);
        outputResource.setDeliveryTypeFlagList(deliveryTypeFlagList);

        // 配達料区分リスト取得
        List<CateringPriceTypeListDto> cateringPriceTypeList = deliveryService
            .getCateringPriceTypeList(inputDto);
        outputResource.setCateringPriceTypeList(cateringPriceTypeList);

        if (null == outputDto.getDeliveryFlag() || "".equals(outputDto.getDeliveryFlag())) {

            outputDto.setDeliveryFlag(Flag.OFF.getCode().toString());
            outputDto.setDeliveryTypeFlag(DeliveryTypeWay.NOWAY.getCode());
        }

        return outputResource;
    }

    /**
     * 指定区域取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 指定区域情報
     */
    @PostMapping(UrlConstants.PC_GET_SELECTED_AREA_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetSelectedAreaListOutputResource getSelectedAreaList(
        @Validated @RequestBody GetSelectedAreaListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetSelectedAreaListInputDto inputDto = beanMapper
            .map(inputResource, GetSelectedAreaListInputDto.class);

        // エリア情報サービス処理を実行する
        GetSelectedAreaListOutputDto outputDto = areaService.getSelectedAreaList(inputDto);

        // インプット情報をDTOにセットする
        GetSelectedAreaListOutputResource outputResource = new GetSelectedAreaListOutputResource();

        outputResource.setAreaList(outputDto.getAreaList());

        return outputResource;
    }

    /**
     * 出前設定情報更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_REGIST_DELIVER_SETTING)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistDelivertSettingOutputResource registDelivertSetting(
        @Validated @RequestBody RegistDelivertSettingInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistDelivertSettingInputDto inputDto = beanMapper
            .map(inputResource, RegistDelivertSettingInputDto.class);

        // 商品編集サービス処理を実行する
        deliveryService.registDelivertSetting(inputDto);

        // 処理結果を設定する
        return new RegistDelivertSettingOutputResource();
    }
}
