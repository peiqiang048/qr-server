package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.DeleteAreaInputResource;
import com.cnc.qr.api.pc.resource.DeleteAreaOutputResource;
import com.cnc.qr.api.pc.resource.DeleteTableInputResource;
import com.cnc.qr.api.pc.resource.DeleteTableOutputResource;
import com.cnc.qr.api.pc.resource.GetAreaInputResource;
import com.cnc.qr.api.pc.resource.GetAreaListInputResource;
import com.cnc.qr.api.pc.resource.GetAreaListOutputResource;
import com.cnc.qr.api.pc.resource.GetAreaOutputResource;
import com.cnc.qr.api.pc.resource.GetTableInputResource;
import com.cnc.qr.api.pc.resource.GetTableListInputResource;
import com.cnc.qr.api.pc.resource.GetTableListOutputResource;
import com.cnc.qr.api.pc.resource.GetTableOutputResource;
import com.cnc.qr.api.pc.resource.RegisterAreaInputResource;
import com.cnc.qr.api.pc.resource.RegisterAreaOutputResource;
import com.cnc.qr.api.pc.resource.RegisterTableInputResource;
import com.cnc.qr.api.pc.resource.RegisterTableOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.DeleteAreaInputDto;
import com.cnc.qr.core.pc.model.DeleteTableInputDto;
import com.cnc.qr.core.pc.model.GetAreaInputDto;
import com.cnc.qr.core.pc.model.GetAreaListInputDto;
import com.cnc.qr.core.pc.model.GetAreaListOutputDto;
import com.cnc.qr.core.pc.model.GetAreaOutputDto;
import com.cnc.qr.core.pc.model.GetTableInputDto;
import com.cnc.qr.core.pc.model.GetTableListInputDto;
import com.cnc.qr.core.pc.model.GetTableListOutputDto;
import com.cnc.qr.core.pc.model.GetTableOutputDto;
import com.cnc.qr.core.pc.model.RegisterAreaInputDto;
import com.cnc.qr.core.pc.model.RegisterTableInputDto;
import com.cnc.qr.core.pc.service.TableMgtService;
import com.github.dozermapper.core.Mapper;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * テーブル管理コントローラ.
 */
@RestController
public class TableMgtController {

    /**
     * テーブル管理サービス.
     */
    @Autowired
    private TableMgtService tableMgtService;

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
     * テーブル一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return テーブル一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_TABLE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetTableListOutputResource getTableList(
        @Validated @RequestBody GetTableListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTableListInputDto inputDto = beanMapper.map(inputResource, GetTableListInputDto.class);

        // テーブル管理サービスを実行する
        GetTableListOutputDto outDto = tableMgtService.getTableList(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetTableListOutputResource outputResource = new GetTableListOutputResource();
        outputResource.setTableList(outDto.getTableList());
        outputResource.setTotalCount(outDto.getTotalCount());

        return outputResource;
    }

    /**
     * テーブル情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return テーブル情報
     */
    @PostMapping(UrlConstants.PC_GET_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetTableOutputResource getTable(
        @Validated @RequestBody GetTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTableInputDto inputDto = beanMapper.map(inputResource, GetTableInputDto.class);

        // テーブル管理サービスを実行する
        GetTableOutputDto outDto = tableMgtService.getTable(inputDto);

        // インプット情報をDTOにセットする
        GetTableOutputResource outputResource = beanMapper
            .map(outDto, GetTableOutputResource.class);
        outputResource.setAreaList(outDto.getAreaList());

        return outputResource;
    }

    /**
     * テーブル情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_REGIST_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegisterTableOutputResource registerTable(
        @Validated @RequestBody RegisterTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegisterTableInputDto inputDto = beanMapper.map(inputResource, RegisterTableInputDto.class);

        // テーブル管理サービスを実行する
        tableMgtService.registerTable(inputDto);

        return new RegisterTableOutputResource();
    }

    /**
     * テーブル情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public DeleteTableOutputResource deleteTable(
        @Validated @RequestBody DeleteTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteTableInputDto inputDto = beanMapper.map(inputResource, DeleteTableInputDto.class);
        inputDto.setTableList(inputResource.getTableList());

        // テーブル管理サービスを実行する
        tableMgtService.deleteTable(inputDto);

        return new DeleteTableOutputResource();
    }

    /**
     * エリア一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return エリア一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_AREA_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetAreaListOutputResource getAreaList(
        @Validated @RequestBody GetAreaListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetAreaListInputDto inputDto = beanMapper.map(inputResource, GetAreaListInputDto.class);

        // テーブル管理サービスを実行する
        GetAreaListOutputDto outDto = tableMgtService.getAreaList(inputDto);

        // インプット情報をDTOにセットする
        GetAreaListOutputResource outputResource = new GetAreaListOutputResource();
        outputResource.setAreaList(outDto.getAreaList());

        return outputResource;
    }

    /**
     * テーブル情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return テーブル情報
     */
    @PostMapping(UrlConstants.PC_GET_AREA_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetAreaOutputResource getArea(
        @Validated @RequestBody GetAreaInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetAreaInputDto inputDto = beanMapper.map(inputResource, GetAreaInputDto.class);

        // テーブル管理サービスを実行する
        GetAreaOutputDto outDto = tableMgtService.getArea(inputDto);

        // インプット情報をDTOにセットする

        return beanMapper.map(outDto, GetAreaOutputResource.class);
    }

    /**
     * エリア情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_REGIST_AREA_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegisterAreaOutputResource registerArea(
        @Validated @RequestBody RegisterAreaInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegisterAreaInputDto inputDto = beanMapper.map(inputResource, RegisterAreaInputDto.class);

        // テーブル管理サービスを実行する
        tableMgtService.registerArea(inputDto);

        return new RegisterAreaOutputResource();
    }

    /**
     * エリア情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_AREA_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public DeleteAreaOutputResource deleteArea(
        @Validated @RequestBody DeleteAreaInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteAreaInputDto inputDto = beanMapper.map(inputResource, DeleteAreaInputDto.class);
        inputDto.setAreaList(inputResource.getAreaList());

        // テーブル管理サービスを実行する
        tableMgtService.deleteArea(inputDto);

        return new DeleteAreaOutputResource();
    }
}
