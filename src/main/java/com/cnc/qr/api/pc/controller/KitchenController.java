package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.DeleteKitchenInputResource;
import com.cnc.qr.api.pc.resource.DeleteKitchenOutputResource;
import com.cnc.qr.api.pc.resource.GetKitchenInputResource;
import com.cnc.qr.api.pc.resource.GetKitchenOutputResource;
import com.cnc.qr.api.pc.resource.KitchenInputResource;
import com.cnc.qr.api.pc.resource.KitchenOutputResource;
import com.cnc.qr.api.pc.resource.RegistKitchenInputResource;
import com.cnc.qr.api.pc.resource.RegistKitchenOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.DeleteKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenOutputDto;
import com.cnc.qr.core.pc.model.KitchenListInputDto;
import com.cnc.qr.core.pc.model.KitchenListOutputDto;
import com.cnc.qr.core.pc.model.RegistKitchenInputDto;
import com.cnc.qr.core.pc.service.KitchenService;
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
 * キッチンコントローラ.
 */
@RestController
public class KitchenController {

    /**
     * キッチン情報取得サービ.
     */
    @Autowired
    private KitchenService kitchenService;

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
     * キッチン一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return キッチン一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_KITCHEN_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public KitchenOutputResource getKitchenList(
        @Validated @RequestBody KitchenInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        KitchenListInputDto inputDto = beanMapper.map(inputResource, KitchenListInputDto.class);

        // キッチン一覧情報取得サービス処理を実行する
        KitchenListOutputDto outDto = kitchenService.getKitchenList(inputDto);

        // インプット情報をDTOにセットする
        KitchenOutputResource outputDto = beanMapper.map(outDto, KitchenOutputResource.class);

        return outputDto;
    }


    /**
     * キッチン情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return キッチン情報
     */
    @PostMapping(UrlConstants.PC_GET_KITCHEN_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetKitchenOutputResource getKitchen(
        @Validated @RequestBody GetKitchenInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetKitchenInputDto inputDto = beanMapper.map(inputResource, GetKitchenInputDto.class);

        // キッチン情報取得サービス処理を実行する
        GetKitchenOutputDto outDto = kitchenService.getKitchen(inputDto);

        // インプット情報をDTOにセットする
        GetKitchenOutputResource outputDto = beanMapper
            .map(outDto, GetKitchenOutputResource.class);

        return outputDto;
    }

    /**
     * キッチン編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_REGIST_KITCHEN_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistKitchenOutputResource registKitchen(
        @Validated @RequestBody RegistKitchenInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistKitchenInputDto inputDto = beanMapper.map(inputResource, RegistKitchenInputDto.class);
        inputDto.setKitchenPrintList(inputResource.getKitchenPrintList());

        // キッチン編集サービス処理を実行する
        kitchenService.registKitchen(inputDto);

        return new RegistKitchenOutputResource();
    }

    /**
     * キッチン情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_DELETE_KITCHEN_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public DeleteKitchenOutputResource deleteKitchen(
        @Validated @RequestBody DeleteKitchenInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        DeleteKitchenInputDto inputDto = beanMapper.map(inputResource, DeleteKitchenInputDto.class);
        inputDto.setKitchenList(inputResource.getKitchenList());

        //  キッチンサービス処理を実行する
        kitchenService.deleteKitchen(inputDto);
        return new DeleteKitchenOutputResource();
    }
}
