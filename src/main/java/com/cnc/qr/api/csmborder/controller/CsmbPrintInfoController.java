package com.cnc.qr.api.csmborder.controller;

import com.alibaba.fastjson.JSON;
import com.cnc.qr.api.csmborder.resource.OrderAccountOutResource;
import com.cnc.qr.api.csmborder.resource.PrintCustomerKitchenOutputResource;
import com.cnc.qr.api.csmborder.resource.QrCodeInputResource;
import com.cnc.qr.api.csmborder.resource.QrCodeOutputResource;
import com.cnc.qr.api.csmborder.resource.SlipInputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.shared.model.OrderAccountDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.QrCodeOutputDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.core.order.model.PrintCustomerKitchenDto;
import com.cnc.qr.core.order.service.PrintInfoService;
import com.github.dozermapper.core.Mapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
 * プリンター伝票コントローラ.
 */
@RestController
public class CsmbPrintInfoController {

    /**
     * プリンター伝票サービス.
     */
    @Autowired
    private PrintInfoService printInfoService;

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
     * 客用厨房印刷.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 客用厨房印刷
     */
    @PostMapping(UrlConstants.PRINT_CUSTOMER_KITCHEN)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public PrintCustomerKitchenOutputResource getPrintInfo(
        @Validated @RequestBody SlipInputResource inputResource, BindingResult result) {

        SlipInputDto inputDto = beanMapper.map(inputResource, SlipInputDto.class);
        PrintCustomerKitchenDto outputDto = printInfoService.getCustomerKitchen(inputDto);
        return beanMapper.map(outputDto, PrintCustomerKitchenOutputResource.class);
    }


    /**
     * QRコード印刷.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return QRコード印刷情報
     */
    @PostMapping(UrlConstants.PRINT_QR_CODE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public QrCodeOutputResource getPrintQrCode(
        @Validated @RequestBody QrCodeInputResource inputResource, BindingResult result) {

        QrCodeInputDto inputDto = beanMapper.map(inputResource, QrCodeInputDto.class);
        QrCodeOutputDto outputDto = printInfoService.getPrintQrCode(inputDto);
        return beanMapper.map(outputDto, QrCodeOutputResource.class);
    }


    /**
     * 会計印刷.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 会計印刷情報
     */
    @PostMapping(UrlConstants.PRINT_ORDER_ACCOUNT)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public OrderAccountOutResource getPrintOrderAccount(
        @Validated @RequestBody SlipInputResource inputResource, BindingResult result) {

        SlipInputDto inputDto = beanMapper.map(inputResource, SlipInputDto.class);
        OrderAccountInfoDto outputDto = printInfoService.getPrintOrderAccount(inputDto);
        return beanMapper.map(outputDto, OrderAccountOutResource.class);
    }
}
