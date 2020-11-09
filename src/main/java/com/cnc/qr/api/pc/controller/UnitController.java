package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.GetUnitInputResource;
import com.cnc.qr.api.pc.resource.GetUnitOutputResource;
import com.cnc.qr.api.pc.resource.RegistUnitInputResource;
import com.cnc.qr.api.pc.resource.RegistUnitOutputResource;
import com.cnc.qr.api.pc.resource.UnitDelInputResource;
import com.cnc.qr.api.pc.resource.UnitDelOutputResource;
import com.cnc.qr.api.pc.resource.UnitInputResource;
import com.cnc.qr.api.pc.resource.UnitOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.GetUnitInputDto;
import com.cnc.qr.core.pc.model.GetUnitOutputDto;
import com.cnc.qr.core.pc.model.RegistUnitInputDto;
import com.cnc.qr.core.pc.model.UnitDelInputDto;
import com.cnc.qr.core.pc.model.UnitListInputDto;
import com.cnc.qr.core.pc.model.UnitListOutputDto;
import com.cnc.qr.core.pc.service.UnitService;
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
 * 单位コントローラ.
 */
@RestController
public class UnitController {

    /**
     * 单位情報取得サービ.
     */
    @Autowired
    private UnitService unitService;

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
     * 单位一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 单位一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_UNIT_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public UnitOutputResource getUnitList(
        @Validated @RequestBody UnitInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        UnitListInputDto inputDto = beanMapper.map(inputResource, UnitListInputDto.class);

        // 单位一覧情報取得サービス処理を実行する
        UnitListOutputDto outDto = unitService.getUnitList(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        UnitOutputResource outputDto = beanMapper.map(outDto, UnitOutputResource.class);

        return outputDto;
    }

    /**
     * 单位情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 单位情報
     */
    @PostMapping(UrlConstants.PC_GET_UNIT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetUnitOutputResource getUnit(
        @Validated @RequestBody GetUnitInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetUnitInputDto inputDto = beanMapper.map(inputResource, GetUnitInputDto.class);

        // 单位情報取得サービス処理を実行する
        GetUnitOutputDto outDto = unitService.getUnit(inputDto);

        // インプット情報をDTOにセットする
        GetUnitOutputResource outputDto = beanMapper
            .map(outDto, GetUnitOutputResource.class);

        return outputDto;
    }

    /**
     * 单位編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 单位保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_UNIT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistUnitOutputResource registUnit(
        @Validated @RequestBody RegistUnitInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistUnitInputDto inputDto = beanMapper.map(inputResource, RegistUnitInputDto.class);

        //  单位サービス処理を実行する
        unitService.saveUnit(inputDto);

        return new RegistUnitOutputResource();
    }

    /**
     * 单位削除情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 单位保存情報
     */
    @PostMapping(UrlConstants.PC_DELETE_UNIT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public UnitDelOutputResource delUnit(
        @Validated @RequestBody UnitDelInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        UnitDelInputDto inputDto = beanMapper.map(inputResource, UnitDelInputDto.class);

        // 单位サービス処理を実行する
        unitService.delUnit(inputDto);

        return new UnitDelOutputResource();
    }
}
