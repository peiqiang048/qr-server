package com.cnc.qr.api.csmborder.controller;

import com.cnc.qr.api.csmborder.resource.TaxInfoInputResource;
import com.cnc.qr.api.csmborder.resource.TaxInfoOutputResource;
import com.cnc.qr.api.csmborder.resource.TaxValueInputResource;
import com.cnc.qr.api.csmborder.resource.TaxValueOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.TaxInfoInputDto;
import com.cnc.qr.core.order.model.TaxInfoOutputDto;
import com.cnc.qr.core.order.model.TaxValueInputDto;
import com.cnc.qr.core.order.model.TaxValueOutputDto;
import com.cnc.qr.core.order.service.TaxInfoService;
import com.github.dozermapper.core.Mapper;
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
 * 税情報コントローラ.
 */
@RestController
public class CsmbTaxInfoController {

    /**
     * 税情報取得サービス.
     */
    @Autowired
    private TaxInfoService taxInfoService;

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
     * 税金额取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税金额
     */
    @PostMapping(UrlConstants.CSMB_GET_TAX_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public TaxValueOutputResource getTaxValue(
        @Validated @RequestBody TaxValueInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        TaxValueInputDto inputDto = beanMapper.map(inputResource, TaxValueInputDto.class);
        inputDto.setItemList(inputResource.getItems());

        // 税情報サービス処理を実行する
        TaxValueOutputDto outputDto = taxInfoService.getTaxValue(inputDto);

        // インプット情報をDTOにセットする
        return beanMapper.map(outputDto, TaxValueOutputResource.class);
    }

    /**
     * 税情報.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税情報
     */
    @PostMapping(UrlConstants.CSMB_GET_TAX_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public TaxInfoOutputResource getTaxInfo(
        @Validated @RequestBody TaxInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        TaxInfoInputDto inputDto = beanMapper.map(inputResource, TaxInfoInputDto.class);

        // 税情報サービス処理を実行する
        TaxInfoOutputDto outputDto = taxInfoService.getTaxInfo(inputDto);

        // インプット情報をDTOにセットする
        return beanMapper.map(outputDto, TaxInfoOutputResource.class);
    }
}
