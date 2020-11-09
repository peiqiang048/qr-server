package com.cnc.qr.api.csmborder.controller;

import com.cnc.qr.api.csmborder.resource.GetStoreAdverPicInputResource;
import com.cnc.qr.api.csmborder.resource.GetStoreAdverPicOutputResource;
import com.cnc.qr.api.csmborder.resource.GetStoreInfoInputResource;
import com.cnc.qr.api.csmborder.resource.GetStoreInfoOutputResource;
import com.cnc.qr.api.csmborder.resource.GetStoreLanguagesInputResource;
import com.cnc.qr.api.csmborder.resource.GetStoreLanguagesOutputResource;
import com.cnc.qr.api.csmborder.resource.GetStorePaymentInfoInputResource;
import com.cnc.qr.api.csmborder.resource.GetStorePaymentInfoOutputResource;
import com.cnc.qr.api.csmborder.resource.GetTableInputResource;
import com.cnc.qr.api.csmborder.resource.GetTableOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.core.order.model.GetActiveLanguageInputDto;
import com.cnc.qr.core.order.model.GetActiveLanguageOutputDto;
import com.cnc.qr.core.order.model.GetPaymentInfoInputDto;
import com.cnc.qr.core.order.model.GetPaymentInfoOutputDto;
import com.cnc.qr.core.order.model.GetStoreAdverPicInputDto;
import com.cnc.qr.core.order.model.GetStoreAdverPicOutputDto;
import com.cnc.qr.core.order.model.GetStoreInfoInputDto;
import com.cnc.qr.core.order.model.GetStoreInfoOutputDto;
import com.cnc.qr.core.order.model.GetTableInfoInputDto;
import com.cnc.qr.core.order.model.GetTableInfoOutputDto;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.core.order.service.TableInfoService;
import com.cnc.qr.security.model.ControlDto;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
 * 店舗情報コントローラ.
 */
@RestController
public class CsmbStoreInfoController {

    /**
     * 店舗情報取得サービス.
     */
    @Autowired
    private StoreInfoService storeInfoService;

    /**
     * 座席情報取得サービス.
     */
    @Autowired
    private TableInfoService tableInfoService;

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
     * 店舗情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗情報
     */
    @PostMapping(UrlConstants.CSMB_GET_STORE_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetStoreInfoOutputResource getStoreInfo(
        @Validated @RequestBody GetStoreInfoInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetStoreInfoInputDto inputDto = beanMapper.map(inputResource, GetStoreInfoInputDto.class);

        // 店舗情報取得サービス処理を実行する
        GetStoreInfoOutputDto outputDto = storeInfoService.getStoreInfo(inputDto);

        // アウトプット情報を作成する
        GetStoreInfoOutputResource outputResource = new GetStoreInfoOutputResource();
        ControlDto controlDto = storeInfoService.getStoreControl(inputDto.getStoreId());
        outputResource.setVoiceOrderAvailableFlag(controlDto.getVoiceOrderAvailableFlag());
        // 店舗情報を設定する
        outputResource.setStoreInfo(outputDto);

        return outputResource;
    }

    /**
     * 店舗媒体情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗媒体情報
     */
    @PostMapping(UrlConstants.CSMB_GET_STORE_ADVERPIC)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetStoreAdverPicOutputResource getStoreAdverPic(
        @Validated @RequestBody GetStoreAdverPicInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                ResultMessages.error()
                    .add(messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN)));
        }

        // インプット情報をDTOにセットする
        GetStoreAdverPicInputDto inputDto = beanMapper
            .map(inputResource, GetStoreAdverPicInputDto.class);
        inputDto.setMediumType(CodeConstants.MediumType.IMAGE.getCode()); //  店舗媒体种类
        inputDto.setTerminalDistinction(TerminalDistinction.CSMB.getCode()); // 店舗媒体用途区分

        // 店舗媒体情報サービス処理を実行する
        GetStoreAdverPicOutputDto outputDto = storeInfoService.getStoreAdverPic(inputDto);

        // アウトプット情報を作成する
        GetStoreAdverPicOutputResource outputResource = new GetStoreAdverPicOutputResource();

        // 店舗媒体情報を設定する
        outputResource.setPicUrls(outputDto.getPicUrls());

        return outputResource;

    }

    /**
     * 店舗言語情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗言語情報
     */
    @PostMapping(UrlConstants.CSMB_GET_ACTIVE_LANGUAGE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetStoreLanguagesOutputResource getStoreAdverPic(
        @Validated @RequestBody GetStoreLanguagesInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                ResultMessages.error()
                    .add(messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN)));
        }

        // インプット情報をDTOにセットする
        GetActiveLanguageInputDto inputDto = beanMapper
            .map(inputResource, GetActiveLanguageInputDto.class);

        // 店舗言語情報取得サービス処理を実行する
        GetActiveLanguageOutputDto outputDto = storeInfoService.getActiveLanguage(inputDto);

        // アウトプット情報を作成する
        GetStoreLanguagesOutputResource outputResource = new GetStoreLanguagesOutputResource();

        // 店舗言語情報を設定する
        outputResource.setLanguages(outputDto.getLanguages());

        return outputResource;

    }

    /**
     * 店舗支払方式情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗支払方式情報
     */
    @PostMapping(UrlConstants.CSMB_GET_STORE_PAYMENT_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetStorePaymentInfoOutputResource getPaymentType(
        @Validated @RequestBody GetStorePaymentInfoInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                ResultMessages.error()
                    .add(messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN)));
        }

        // インプット情報をDTOにセットする
        GetPaymentInfoInputDto inputDto = beanMapper
            .map(inputResource, GetPaymentInfoInputDto.class);

        // 支払方式取得サービス処理を実行する
        GetPaymentInfoOutputDto outputDto = storeInfoService.getPaymentType(inputDto);

        // 支払方式情報を設定する
        GetStorePaymentInfoOutputResource outputResource = new GetStorePaymentInfoOutputResource();
        outputResource.setPaymentType(outputDto.getPaymentType());
        List<PaymentMethodDto> paymentMethodList = new ArrayList<>();
        outputDto.getPaymentMethodList().forEach(paymentMethodDto -> {
            if (Integer.parseInt(paymentMethodDto.getPaymentCode()) < 21 && 0 < Integer
                .parseInt(paymentMethodDto.getPaymentCode())) {
                paymentMethodList.add(paymentMethodDto);
            }
        });
        outputResource.setPaymentMethodList(paymentMethodList);

        return outputResource;

    }

    /**
     * テーブル情報.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return テーブル情報取得
     */
    @PostMapping(UrlConstants.CSMB_GET_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetTableOutputResource getTable(
        @Validated @RequestBody GetTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTableInfoInputDto inputDto = beanMapper.map(inputResource, GetTableInfoInputDto.class);

        // テーブルサービス処理を実行する
        GetTableInfoOutputDto outputDto = tableInfoService.getTableInfo(inputDto);

        return beanMapper.map(outputDto, GetTableOutputResource.class);
    }
}
