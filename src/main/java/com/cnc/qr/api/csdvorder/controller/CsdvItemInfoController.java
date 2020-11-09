package com.cnc.qr.api.csdvorder.controller;

import com.cnc.qr.api.csdvorder.constants.CsdvConstants;
import com.cnc.qr.api.csdvorder.resource.GetItemCategoryInputResource;
import com.cnc.qr.api.csdvorder.resource.GetItemCategoryOutputResource;
import com.cnc.qr.api.csdvorder.resource.GetItemInputResource;
import com.cnc.qr.api.csdvorder.resource.GetItemOptionTypeInputResource;
import com.cnc.qr.api.csdvorder.resource.GetItemOptionTypeOutputResource;
import com.cnc.qr.api.csdvorder.resource.GetItemOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.GetDeliveryCategoryInputDto;
import com.cnc.qr.core.order.model.GetDeliveryCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryItemDto;
import com.cnc.qr.core.order.model.GetDeliveryItemInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeOutputDto;
import com.cnc.qr.core.order.service.ItemService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 商品情報コントローラ.
 */
@RestController
public class CsdvItemInfoController {

    /**
     * 商品情報取得サービス.
     */
    @Autowired
    private ItemService itemService;

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
     * 出前商品カテゴリー情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品カテゴリー情報
     */
    @PostMapping(UrlConstants.CSDV_GET_ITEM_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetItemCategoryOutputResource getItemCategory(
        @Validated @RequestBody GetItemCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryCategoryInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryCategoryInputDto.class);
        inputDto.setGradation(CsdvConstants.GRADATION);

        // サービス処理を実行する
        GetDeliveryCategoryListOutputDto outputDto = itemService.getDeliveryCategoryList(inputDto);

        // アウトプット情報を作成する
        GetItemCategoryOutputResource outputResource = new GetItemCategoryOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setItemCategoryList(outputDto.getItemCategoryList());

        return outputResource;
    }

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param pageable      ページ情報
     * @param result        バインド結果
     * @return 商品情報
     */
    @GetMapping(UrlConstants.CSDV_GET_ITEM_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public ResponseEntity<GetItemOutputResource> getItemList(
        @Validated GetItemInputResource inputResource,
        @PageableDefault(size = 8) Pageable pageable, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryItemInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryItemInputDto.class);

        // サービス処理を実行する
        Page<GetDeliveryItemDto> page = itemService.getDeliveryItemList(inputDto, pageable);

        // インプット情報をDTOにセットする
        GetItemOutputResource outputResource = new GetItemOutputResource();

        // 商品情報を設定する
        outputResource.setItemList(page.getContent());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 商品オプション情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品オプション情報
     */
    @PostMapping(UrlConstants.CSDV_GET_ITEM_OPTION_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetItemOptionTypeOutputResource getItemOption(
        @Validated @RequestBody GetItemOptionTypeInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemOptionTypeInputDto inputDto =
            beanMapper.map(inputResource, GetItemOptionTypeInputDto.class);

        // サービス処理を実行する
        GetItemOptionTypeOutputDto outputDto = itemService.getItemOptionType(inputDto);

        // アウトプット情報を作成する
        GetItemOptionTypeOutputResource outputResource = new GetItemOptionTypeOutputResource();

        // 商品オプション類型情報を設定する
        outputResource.setOptionTypeList(outputDto.getOptionTypeList());

        return outputResource;
    }


}
