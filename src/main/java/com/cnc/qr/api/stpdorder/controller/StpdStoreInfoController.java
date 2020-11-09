package com.cnc.qr.api.stpdorder.controller;

import com.alibaba.fastjson.JSON;
import com.cnc.qr.api.stpdorder.resource.AccountingPrintInputResource;
import com.cnc.qr.api.stpdorder.resource.AccountingPrintPrintOutputResource;
import com.cnc.qr.api.stpdorder.resource.InspectionSettleInitInputResource;
import com.cnc.qr.api.stpdorder.resource.InspectionSettleInitOutputResource;
import com.cnc.qr.api.stpdorder.resource.InspectionSettleInputResource;
import com.cnc.qr.api.stpdorder.resource.InspectionSettleOutputResource;
import com.cnc.qr.api.stpdorder.resource.MenuInputResource;
import com.cnc.qr.api.stpdorder.resource.MenuOutputResource;
import com.cnc.qr.api.stpdorder.resource.OpenCashDoorInputResource;
import com.cnc.qr.api.stpdorder.resource.OpenCashDoorOutputResource;
import com.cnc.qr.api.stpdorder.resource.PaymentTypeInputResource;
import com.cnc.qr.api.stpdorder.resource.PaymentTypeOutputResource;
import com.cnc.qr.api.stpdorder.resource.PrintDeliverOrderInputResource;
import com.cnc.qr.api.stpdorder.resource.PrintDeliverOrderOutputResource;
import com.cnc.qr.api.stpdorder.resource.ReceiptPrintInputResource;
import com.cnc.qr.api.stpdorder.resource.ReceiptPrintOutputResource;
import com.cnc.qr.api.stpdorder.resource.StoreDeviceRegistInputResource;
import com.cnc.qr.api.stpdorder.resource.StoreDeviceRegistOutputResource;
import com.cnc.qr.api.stpdorder.resource.StoreIdVerificationInputResource;
import com.cnc.qr.api.stpdorder.resource.StoreIdVerificationOutputResource;
import com.cnc.qr.api.stpdorder.resource.UserAndLanguageInputResource;
import com.cnc.qr.api.stpdorder.resource.UserAndLanguageOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.core.order.model.GetActiveLanguageInputDto;
import com.cnc.qr.core.order.model.GetActiveLanguageOutputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoInputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoOutputDto;
import com.cnc.qr.core.order.model.InspectionSettleInitInputDto;
import com.cnc.qr.core.order.model.InspectionSettleInitOutputDto;
import com.cnc.qr.core.order.model.InspectionSettleInputDto;
import com.cnc.qr.core.order.model.MenuInputDto;
import com.cnc.qr.core.order.model.MenuOutputDto;
import com.cnc.qr.core.order.model.PrintDeliverOrderInputDto;
import com.cnc.qr.core.order.model.StoreDeviceRegistInputDto;
import com.cnc.qr.core.order.model.StoreIdVerificationInputDto;
import com.cnc.qr.core.order.model.UserDto;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
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
 * 店舗コントローラ.
 */
@RestController
public class StpdStoreInfoController {

    /**
     * 座席情報取得サービス.
     */
    @Autowired
    private StoreInfoService storeInfoService;

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
     * プリンターデータ取得.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;

    /**
     * ログイン初期化情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return ログイン初期化情報
     */
    @PostMapping(UrlConstants.STPD_LOGIN_INI_URL)
    @ResponseStatus(HttpStatus.OK)
    public UserAndLanguageOutputResource loginIni(
        @Validated @RequestBody UserAndLanguageInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetActiveLanguageInputDto languageInputDto = beanMapper
            .map(inputResource, GetActiveLanguageInputDto.class);

        // 言語リスト取得
        GetActiveLanguageOutputDto languageOutputDto = storeInfoService
            .getActiveLanguage(languageInputDto);
        UserAndLanguageOutputResource outputResource = new UserAndLanguageOutputResource();
        outputResource.setLanguageList(languageOutputDto.getLanguages());

        // ユーザリスト
        List<UserDto> userList = storeInfoService.getUser(inputResource.getStoreId());
        outputResource.setUserList(userList);

        return outputResource;
    }

    /**
     * メニュー情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return メニュー情報
     */
    @PostMapping(UrlConstants.STPD_MENU_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public MenuOutputResource getMenu(
        @Validated @RequestBody MenuInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        MenuInputDto inputDto = beanMapper.map(inputResource, MenuInputDto.class);

        // メニューリスト取得
        MenuOutputDto outputDto = storeInfoService.getMenu(inputDto);

        // インプット情報をDTOにセットする
        return beanMapper.map(outputDto, MenuOutputResource.class);
    }

    /**
     * 後払い.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 後払結果
     */
    @PostMapping(UrlConstants.PAYMENT_TYPE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public PaymentTypeOutputResource getPaymentTypeList(
        @Validated @RequestBody PaymentTypeInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.getDefault()));
        }
        GetPayLaterPaymentInfoInputDto paymentInfoInputDto = beanMapper
            .map(inputResource, GetPayLaterPaymentInfoInputDto.class);

        // 支払い方式取得サービス処理を実行する
        GetPayLaterPaymentInfoOutputDto paymentOutfoInputDto = storeInfoService
            .getPayLaterPaymentType(paymentInfoInputDto);

        // 支払方式情報を設定する
        PaymentTypeOutputResource outputResource = new PaymentTypeOutputResource();
        outputResource.setPaymentTypeList(paymentOutfoInputDto.getPaymentMethodList());

        return outputResource;
    }

    /**
     * 店舗情報検証.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_ID_VERIFICATION_URL)
    @ResponseStatus(HttpStatus.OK)
    public StoreIdVerificationOutputResource storeIdVerification(
        @Validated @RequestBody StoreIdVerificationInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        StoreIdVerificationInputDto languageInputDto = beanMapper
            .map(inputResource, StoreIdVerificationInputDto.class);

        // 店舗情報検証
        storeInfoService.storeIdVerification(languageInputDto);

        return new StoreIdVerificationOutputResource();
    }


    /**
     * 端末情報登録.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_DEVICE_REGIST_URL)
    @ResponseStatus(HttpStatus.OK)
    public StoreDeviceRegistOutputResource storeDeviceRegist(
        @Validated @RequestBody StoreDeviceRegistInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        StoreDeviceRegistInputDto inputDto = beanMapper
            .map(inputResource, StoreDeviceRegistInputDto.class);

        // 店舗情報検証
        storeInfoService.storeDeviceRegist(inputDto);

        return new StoreDeviceRegistOutputResource();
    }

    /**
     * 領収書印刷.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_RECEIPT_PRINT_URL)
    @ResponseStatus(HttpStatus.OK)
    public ReceiptPrintOutputResource storeReceiptPrint(
        @Validated @RequestBody ReceiptPrintInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        // 印刷データ取得
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setStoreId(inputResource.getStoreId());
        slipInputDto.setPaymentAmount(new BigDecimal(inputResource.getPaymentAmount()));
        slipInputDto.setOrderSummaryId(inputResource.getOrderSummaryId());
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setReceiptInfoDto(printDataSharedService.getReceiptPrintData(slipInputDto));

        ReceiptPrintOutputResource outputResource = new ReceiptPrintOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        return outputResource;
    }

    /**
     * 会計印刷(後で印刷).
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_ACCOUNTINGPRINT_PRINT_URL)
    @ResponseStatus(HttpStatus.OK)
    public AccountingPrintPrintOutputResource storeAccountingPrintPrint(
        @Validated @RequestBody AccountingPrintInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        String printInfo = storeInfoService
            .accountingPrintPrint(inputResource.getStoreId(), inputResource.getReceivablesId());
        AccountingPrintPrintOutputResource outputResource = new AccountingPrintPrintOutputResource();
        outputResource.setPrintInfo(printInfo);
        return outputResource;
    }

    /**
     * キャッシュトーアオーペン.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_OPEN_CASH_DOOR_URL)
    @ResponseStatus(HttpStatus.OK)
    public OpenCashDoorOutputResource storeOpenCashDoor(
        @Validated @RequestBody OpenCashDoorInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        String printIp = storeInfoService
            .getOpenCashDoorIp(inputResource.getStoreId());
        OpenCashDoorOutputResource outputResource = new OpenCashDoorOutputResource();
        outputResource.setPrintIp(printIp);
        return outputResource;
    }

    /**
     * 点検精算初期化.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_INSPECTION_SETTLE_INIT_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.INSPECTION_SETTLE_REPORT + "\")")
    @ResponseStatus(HttpStatus.OK)
    public InspectionSettleInitOutputResource storeInspectionSettleInit(
        @Validated @RequestBody InspectionSettleInitInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        InspectionSettleInitInputDto inputDto = beanMapper
            .map(inputResource, InspectionSettleInitInputDto.class);

        // 点検精算初期化情報取得
        InspectionSettleInitOutputDto outputDto = storeInfoService
            .getInspectionSettleInit(inputDto);
        InspectionSettleInitOutputResource outputResource = beanMapper
            .map(outputDto, InspectionSettleInitOutputResource.class);

        return outputResource;
    }


    /**
     * 点検精算.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_STORE_INSPECTION_SETTLE_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.INSPECTION_SETTLE_REPORT + "\")")
    @ResponseStatus(HttpStatus.OK)
    public InspectionSettleOutputResource storeInspectionSettle(
        @Validated @RequestBody InspectionSettleInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        InspectionSettleInputDto inputDto = beanMapper
            .map(inputResource, InspectionSettleInputDto.class);
        // 点検精算情報取得
        storeInfoService
            .registInspectionSettle(inputDto);
        InspectionSettleOutputResource outputResource = new InspectionSettleOutputResource();
        // 点検精算伝票印刷データ取得
        outputResource.setPrintInfo(printDataSharedService
            .inspectionSettlePrint(inputDto.getStoreId(), inputDto.getSettleType()));
        return outputResource;
    }


    /**
     * 注文印刷(出前).
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 検証結果
     */
    @PostMapping(UrlConstants.STPD_PRINT_DELIVERY_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    public PrintDeliverOrderOutputResource storePrintDeliverOrder(
        @Validated @RequestBody PrintDeliverOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }
        PrintDeliverOrderInputDto inputDto = beanMapper
            .map(inputResource, PrintDeliverOrderInputDto.class);

        // 注文印刷(出前)
        PrintDeliverOrderOutputResource outputResource = new PrintDeliverOrderOutputResource();
        outputResource.setPrintInfo(storeInfoService
            .printDeliverOrder(inputDto));

        return outputResource;
    }
}
