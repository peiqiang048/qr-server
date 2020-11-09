package com.cnc.qr.api.stpdorder.controller;

import com.cnc.qr.api.stpdorder.resource.AreaTypeInputResource;
import com.cnc.qr.api.stpdorder.resource.AreaTypeOutputResource;
import com.cnc.qr.api.stpdorder.resource.ChangeTableInputResource;
import com.cnc.qr.api.stpdorder.resource.ChangeTableOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetTableListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetTableListOutputResource;
import com.cnc.qr.api.stpdorder.resource.TableInputResource;
import com.cnc.qr.api.stpdorder.resource.TableOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.AreaTypeInputDto;
import com.cnc.qr.core.order.model.AreaTypeOutputDto;
import com.cnc.qr.core.order.model.ChangeTableInputDto;
import com.cnc.qr.core.order.model.GetTableListInputDto;
import com.cnc.qr.core.order.model.GetTableListOutputDto;
import com.cnc.qr.core.order.model.TableInputDto;
import com.cnc.qr.core.order.model.TableOutputDto;
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
public class StpdTableInfoController {

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
     * 座席一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 座席一覧情報
     */
    @PostMapping(UrlConstants.STPD_GET_TABLE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
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
        GetTableListOutputDto outputDto = tableInfoService.getTableList(inputDto);

        // アウトプット情報を作成する
        GetTableListOutputResource outputResource = new GetTableListOutputResource();

        // テーブル情報リストを設定する
        outputResource.setTableList(outputDto.getTableList());

        return outputResource;
    }

    /**
     * 座席変更.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 変更結果
     */
    @PostMapping(UrlConstants.STPD_CHANGE_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ChangeTableOutputResource changeTable(
        @Validated @RequestBody ChangeTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeTableInputDto inputDto = beanMapper.map(inputResource, ChangeTableInputDto.class);

        // 座席変更サービス処理を実行する
        tableInfoService.changeTable(inputDto);

        return new ChangeTableOutputResource();
    }

    /**
     * エリア情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return エリア情報
     */
    @PostMapping(UrlConstants.STPD_GET_TABLE_AREA_TYPE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public AreaTypeOutputResource getAreaTypeList(
        @Validated @RequestBody AreaTypeInputResource inputResource, BindingResult result) {

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
        return beanMapper.map(outputDto, AreaTypeOutputResource.class);
    }

    /**
     * テーブル情報.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return テーブル情報取得
     */
    @PostMapping(UrlConstants.STPD_GET_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public TableOutputResource getTable(
        @Validated @RequestBody TableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        TableInputDto inputDto = beanMapper.map(inputResource, TableInputDto.class);

        // テーブルサービス処理を実行する
        TableOutputDto outputDto = tableInfoService.getTable(inputDto);

        return beanMapper.map(outputDto, TableOutputResource.class);
    }
}
