package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.GetPrintInputResource;
import com.cnc.qr.api.pc.resource.GetPrintOutputResource;
import com.cnc.qr.api.pc.resource.PrintDelInputResource;
import com.cnc.qr.api.pc.resource.PrintDelOutputResource;
import com.cnc.qr.api.pc.resource.PrintInputResource;
import com.cnc.qr.api.pc.resource.PrintOutputResource;
import com.cnc.qr.api.pc.resource.RegistPrintInputResource;
import com.cnc.qr.api.pc.resource.RegistPrintOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.GetPrintInputDto;
import com.cnc.qr.core.pc.model.GetPrintOutputDto;
import com.cnc.qr.core.pc.model.PrintDelInputDto;
import com.cnc.qr.core.pc.model.PrintListInputDto;
import com.cnc.qr.core.pc.model.PrintListOutputDto;
import com.cnc.qr.core.pc.model.RegistPrintInputDto;
import com.cnc.qr.core.pc.service.PrintService;
import com.github.dozermapper.core.Mapper;
import io.micrometer.core.instrument.util.StringUtils;
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
 * プリンターコントローラ.
 */
@RestController
public class PrintController {

    /**
     * プリンター情報取得サービ.
     */
    @Autowired
    private PrintService printService;

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
     * プリンター一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_PRINT_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public PrintOutputResource getPrintList(
        @Validated @RequestBody PrintInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        PrintListInputDto inputDto = beanMapper.map(inputResource, PrintListInputDto.class);

        // プリンター一覧情報取得サービス処理を実行する
        PrintListOutputDto outDto = printService.getPrintList(inputDto);

        // インプット情報をDTOにセットする
        PrintOutputResource outputDto = beanMapper.map(outDto, PrintOutputResource.class);

        return outputDto;
    }


    /**
     * プリンター情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター情報
     */
    @PostMapping(UrlConstants.PC_GET_PRINT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetPrintOutputResource getPrint(
        @Validated @RequestBody GetPrintInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetPrintInputDto inputDto = beanMapper.map(inputResource, GetPrintInputDto.class);

        // キッチン情報取得サービス処理を実行する
        GetPrintOutputDto outDto = printService.getPrint(inputDto);

        // インプット情報をDTOにセットする
        GetPrintOutputResource outputDto = beanMapper
            .map(outDto, GetPrintOutputResource.class);

        return outputDto;
    }

    /**
     * プリンター編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_PRINT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistPrintOutputResource registPrint(
        @Validated @RequestBody RegistPrintInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistPrintInputDto inputDto = beanMapper.map(inputResource, RegistPrintInputDto.class);

        //  プリンターIPとブルートゥース名どちらがいずれか必須入力。
        if (StringUtils.isBlank(inputDto.getPrintIp())
            && StringUtils.isBlank(inputDto.getBlueToothName())) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.078", null, Locale.JAPAN));
        }

        //  キッチンサービス処理を実行する
        printService.savePrint(inputDto);

        return new RegistPrintOutputResource();
    }

    /**
     * プリンター削除情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター保存情報
     */
    @PostMapping(UrlConstants.PC_DELETE_PRINT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public PrintDelOutputResource delPrint(
        @Validated @RequestBody PrintDelInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        PrintDelInputDto inputDto = beanMapper.map(inputResource, PrintDelInputDto.class);
        inputDto.setPrintList(inputResource.getPrintList());
        //  プリンターサービス処理を実行する
        printService.delPrint(inputDto);

        return new PrintDelOutputResource();
    }
}
