package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.GetTaxInputResource;
import com.cnc.qr.api.pc.resource.GetTaxOutputResource;
import com.cnc.qr.api.pc.resource.RegistTaxInputResource;
import com.cnc.qr.api.pc.resource.RegistTaxOutputResource;
import com.cnc.qr.api.pc.resource.TaxDelInputResource;
import com.cnc.qr.api.pc.resource.TaxDelOutputResource;
import com.cnc.qr.api.pc.resource.TaxInputResource;
import com.cnc.qr.api.pc.resource.TaxOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.GetTaxInputDto;
import com.cnc.qr.core.pc.model.GetTaxOutputDto;
import com.cnc.qr.core.pc.model.RegistTaxInputDto;
import com.cnc.qr.core.pc.model.TaxDelInputDto;
import com.cnc.qr.core.pc.model.TaxListInputDto;
import com.cnc.qr.core.pc.model.TaxListOutputDto;
import com.cnc.qr.core.pc.service.TaxService;
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
 * 税コントローラ.
 */
@RestController
public class TaxController {

    /**
     * 税設定情報取得サービ.
     */
    @Autowired
    private TaxService taxService;

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
     * 税一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_TAX_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public TaxOutputResource getTaxList(
        @Validated @RequestBody TaxInputResource inputResource, BindingResult result) {

        // 共通チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        TaxListInputDto inputDto = beanMapper.map(inputResource, TaxListInputDto.class);

        // 税設定一覧情報取得サービス処理を実行する
        TaxListOutputDto outDto = taxService.getTaxList(inputDto);

        // インプット情報をDTOにセットする
        TaxOutputResource outputDto = beanMapper.map(outDto, TaxOutputResource.class);

        return outputDto;
    }

    /**
     * 税設定情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税設定情報
     */
    @PostMapping(UrlConstants.PC_GET_TAX_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetTaxOutputResource getTax(
        @Validated @RequestBody GetTaxInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTaxInputDto inputDto = beanMapper.map(inputResource, GetTaxInputDto.class);

        // 税設定情報取得サービス処理を実行する
        GetTaxOutputDto outDto = taxService.getTax(inputDto);

        // インプット情報をDTOにセットする
        GetTaxOutputResource outputDto = beanMapper
            .map(outDto, GetTaxOutputResource.class);

        return outputDto;
    }

    /**
     * 税設定編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税設定保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_TAX_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistTaxOutputResource registTax(
        @Validated @RequestBody RegistTaxInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistTaxInputDto inputDto = beanMapper.map(inputResource, RegistTaxInputDto.class);

        //  税設定サービス処理を実行する
        taxService.saveTax(inputDto);

        return new RegistTaxOutputResource();
    }

    /**
     * 税設定削除情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 税設定保存情報
     */
    @PostMapping(UrlConstants.PC_DELETE_TAX_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public TaxDelOutputResource delUnit(
        @Validated @RequestBody TaxDelInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        TaxDelInputDto inputDto = beanMapper.map(inputResource, TaxDelInputDto.class);
        inputDto.setTaxIdList(inputResource.getTaxIdList());

        // 税設定サービス処理を実行する
        taxService.delTax(inputDto);

        return new TaxDelOutputResource();
    }
}
