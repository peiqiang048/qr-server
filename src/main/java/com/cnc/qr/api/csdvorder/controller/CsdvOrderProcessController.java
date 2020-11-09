package com.cnc.qr.api.csdvorder.controller;

import com.cnc.qr.api.csdvorder.resource.GetDeliveryOrderInputResource;
import com.cnc.qr.api.csdvorder.resource.GetDeliveryOrderOutputResource;
import com.cnc.qr.api.csdvorder.resource.GetOrderInfoInputResource;
import com.cnc.qr.api.csdvorder.resource.GetOrderInfoOutputResource;
import com.cnc.qr.api.csdvorder.resource.GetSelectedAreaListInputResource;
import com.cnc.qr.api.csdvorder.resource.GetSelectedAreaListOutputResource;
import com.cnc.qr.api.csdvorder.resource.OrderInputResource;
import com.cnc.qr.api.csdvorder.resource.OrderOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.push.FcmPush;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.DeliveryOrderInputDto;
import com.cnc.qr.core.order.model.DeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.GetAreaDevInfoDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.GetOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.github.dozermapper.core.Mapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
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
 * 注文処理コントローラ.
 */
@RestController
public class CsdvOrderProcessController {

    /**
     * 注文済情報取得サービス.
     */
    @Autowired
    private OrderService orderService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 店舗サービス.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 共通部品プリンター.
     */
    @Autowired
    FcmPush fcmPush;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 注文情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文情報
     */
    @PostMapping(UrlConstants.CSDV_GET_DELIVERY_ORDER_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetOrderInfoOutputResource getDeliveryOrderInfo(
        @Validated @RequestBody GetOrderInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderInfoInputDto inputDto = beanMapper.map(inputResource, GetOrderInfoInputDto.class);

        // 注文情報取得サービス処理を実行する
        GetDeliveryOrderInfoOutputDto outputDto = orderService.getDeliveryOrderInfo(inputDto);

        // 注文商品情報リストを設定する
        return beanMapper.map(outputDto, GetOrderInfoOutputResource.class);
    }

    /**
     * 注文確定.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文確定の結果
     */
    @PostMapping(UrlConstants.CSDV_REGIST_DELIVERY_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public OrderOutputResource registDeliveryOrder(
        @Validated @RequestBody OrderInputResource inputResource,
        BindingResult result, HttpServletRequest request) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeliveryOrderInputDto inputDto = beanMapper.map(inputResource, DeliveryOrderInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // 現在の時刻
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        String formatter = "yyyy-MM-dd HH:mm:ss";

        // 出前開始時間
        String deliveryStartTime = DateUtil.getZonedDateString(nowDateTime, formatter).split(" ")[0]
            + " " + inputResource.getDeliveryTime().split("～")[0] + ":00";

        // 出前終了時間
        String deliveryEndTime = DateUtil.getZonedDateString(nowDateTime, formatter).split(" ")[0]
            + " " + inputResource.getDeliveryTime().split("～")[1] + ":00";

        if (DateUtil.getZonedDateByString(deliveryStartTime, formatter).isBefore(nowDateTime)) {

            deliveryStartTime = DateUtil.getZonedDateString(
                DateUtil.getZonedDateByString(deliveryStartTime, formatter).plusDays(1L),
                formatter);
            deliveryEndTime = DateUtil.getZonedDateString(
                DateUtil.getZonedDateByString(deliveryEndTime, formatter).plusDays(1L), formatter);
        }

        inputDto.setDeliveryStartTime(DateUtil.getZonedDateByString(deliveryStartTime, formatter));
        inputDto.setDeliveryEndTime(DateUtil.getZonedDateByString(deliveryEndTime, formatter));

        // サービス処理を実行するDto
        DeliveryOrderOutputDto outputDto = orderService.registDeliveryOrder(inputDto);

        // アウトプット情報を作成する
        return beanMapper.map(outputDto, OrderOutputResource.class);
    }

    /**
     * 注文編集情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文編集情報
     */
    @PostMapping(UrlConstants.CSDV_GET_DELIVERY_ORDER_EDIT_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetDeliveryOrderOutputResource getDeliveryOrder(
        @Validated @RequestBody GetDeliveryOrderInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryOrderInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryOrderInputDto.class);

        // サービス処理を実行する
        GetDeliveryOrderOutputDto outputDto = orderService.getDeliveryOrderNewEditInfo(inputDto);

        // アウトプット情報を作成する
        GetDeliveryOrderOutputResource outputResource = new GetDeliveryOrderOutputResource();

        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }

    /**
     * 店舗指定区域取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗指定区域情報
     */
    @PostMapping(UrlConstants.CSDV_GET_SELECTDE_AREA_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
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

        // 店舗指定区域情報取得サービス処理を実行する
        GetSelectedAreaListOutputDto outputDto = orderService.getSelectedAreaList(inputDto);

        // インプット情報をDTOにセットする
        GetSelectedAreaListOutputResource outputResource = new GetSelectedAreaListOutputResource();

        // 店舗指定区域情報を設定する
        List<GetAreaDevInfoDto> areaList = new ArrayList<>();
        outputDto.getAreaList().forEach(areaInfoDto -> {
            GetAreaDevInfoDto areaInfo = new GetAreaDevInfoDto();
            areaInfo.setAreaId(areaInfoDto.getAreaId());
            areaInfo.setAreaName(areaInfoDto.getAreaName());
            areaList.add(areaInfo);
        });

        outputResource.setAreaList(areaList);

        return outputResource;
    }
}
