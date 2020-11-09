package com.cnc.qr.api.stmborder.controller;

import com.cnc.qr.api.stmborder.resource.GetAreaTypeListInputResource;
import com.cnc.qr.api.stmborder.resource.GetAreaTypeListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetTableListInputResource;
import com.cnc.qr.api.stmborder.resource.GetTableListOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.AreaTypeInputDto;
import com.cnc.qr.core.order.model.AreaTypeOutputDto;
import com.cnc.qr.core.order.model.GetAreaTableListOutputDto;
import com.cnc.qr.core.order.model.GetTableListInputDto;
import com.cnc.qr.core.order.service.TableInfoService;
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
 * 座席情報コントローラ.
 */
@RestController
public class StmbTableInfoController {

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
     * エリア情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return エリア情報
     */
    @PostMapping(UrlConstants.STMB_GET_AREA_TYPE_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public GetAreaTypeListOutputResource getAreaTypeList(
        @Validated @RequestBody GetAreaTypeListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        AreaTypeInputDto inputDto = beanMapper.map(inputResource, AreaTypeInputDto.class);

        // 座席情報取得サービス処理を実行する
        AreaTypeOutputDto outputDto = tableInfoService.getAreaTypeList(inputDto);

        // アウトプット情報を作成する
        return beanMapper.map(outputDto, GetAreaTypeListOutputResource.class);
    }

    /**
     * 座席一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 座席一覧情報
     */
    @PostMapping(UrlConstants.STMB_GET_TABLE_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public GetTableListOutputResource getTableList(
        @Validated @RequestBody GetTableListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTableListInputDto inputDto = beanMapper.map(inputResource, GetTableListInputDto.class);

        // 座席情報取得サービス処理を実行する
        GetAreaTableListOutputDto outputDto = tableInfoService.getAreaTableList(inputDto);

        // アウトプット情報を作成する
        GetTableListOutputResource outputResource = new GetTableListOutputResource();

        // テーブル情報リストを設定する
        outputResource.setTableList(outputDto.getTableList());

        return outputResource;
    }
}
