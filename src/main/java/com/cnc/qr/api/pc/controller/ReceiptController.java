package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.GetReceiptInputResource;
import com.cnc.qr.api.pc.resource.GetReceiptOutputResource;
import com.cnc.qr.api.pc.resource.ReceiptInputResource;
import com.cnc.qr.api.pc.resource.ReceiptOutputResource;
import com.cnc.qr.api.pc.resource.RegistReceiptInputResource;
import com.cnc.qr.api.pc.resource.RegistReceiptOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.GetReceiptInputDto;
import com.cnc.qr.core.pc.model.GetReceiptOutputDto;
import com.cnc.qr.core.pc.model.ReceiptListInputDto;
import com.cnc.qr.core.pc.model.ReceiptListOutputDto;
import com.cnc.qr.core.pc.model.RegistReceiptInputDto;
import com.cnc.qr.core.pc.service.ReceiptService;
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

//import com.cnc.qr.api.pc.resource.PrintDelInputResource;
//import com.cnc.qr.api.pc.resource.PrintDelOutputResource;
//import com.cnc.qr.core.pc.model.PrintDelInputDto;

//import io.micrometer.core.instrument.util.StringUtils;

/**
 * プリンターコントローラ.
 */
@RestController
public class ReceiptController {

    /**
     * レシート情報取得サービ.
     */
    @Autowired
    private ReceiptService receiptService;

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
     * レシート一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return レシート一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_RECEIPT_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public ReceiptOutputResource getReceiptList(
        @Validated @RequestBody ReceiptInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ReceiptListInputDto inputDto = beanMapper.map(inputResource, ReceiptListInputDto.class);

        // プレシート一覧情報取得サービス処理を実行する
        ReceiptListOutputDto outDto = receiptService.getReceiptList(inputDto);

        // インプット情報をDTOにセットする
        ReceiptOutputResource outputDto = beanMapper.map(outDto, ReceiptOutputResource.class);

        return outputDto;
    }


    /**
     * レシート情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return レシート情報
     */
    @PostMapping(UrlConstants.PC_GET_RECEIPT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetReceiptOutputResource getReceipt(
        @Validated @RequestBody GetReceiptInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetReceiptInputDto inputDto = beanMapper.map(inputResource, GetReceiptInputDto.class);

        // レシート情報取得サービス処理を実行する
        GetReceiptOutputDto outDto = receiptService.getReceipt(inputDto);

        // インプット情報をDTOにセットする
        GetReceiptOutputResource outputDto = beanMapper
            .map(outDto, GetReceiptOutputResource.class);

        return outputDto;
    }

    /**
     * レシート編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return レシート保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_RECEIPT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistReceiptOutputResource registReceipt(
        @Validated @RequestBody RegistReceiptInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistReceiptInputDto inputDto = beanMapper.map(inputResource, RegistReceiptInputDto.class);

        //  キッチンサービス処理を実行する
        receiptService.saveReceipt(inputDto);

        return new RegistReceiptOutputResource();
    }
}
