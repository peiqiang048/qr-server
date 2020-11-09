package com.cnc.qr.api.stpdorder.controller;

import com.cnc.qr.api.stpdorder.resource.CancelReservateInputResource;
import com.cnc.qr.api.stpdorder.resource.CancelReservateOutputResource;
import com.cnc.qr.api.stpdorder.resource.FinishReservateInputResource;
import com.cnc.qr.api.stpdorder.resource.FinishReservateOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetAllTableListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetAllTableListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetReservateInputResource;
import com.cnc.qr.api.stpdorder.resource.GetReservateListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetReservateListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetReservateOutputResource;
import com.cnc.qr.api.stpdorder.resource.RegistReservateInputResource;
import com.cnc.qr.api.stpdorder.resource.RegistReservateOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.CancelReservateInputDto;
import com.cnc.qr.core.order.model.FinishReservateInputDto;
import com.cnc.qr.core.order.model.GetAllTableListInputDto;
import com.cnc.qr.core.order.model.GetAllTableListOutputDto;
import com.cnc.qr.core.order.model.GetReservateInputDto;
import com.cnc.qr.core.order.model.GetReservateListInputDto;
import com.cnc.qr.core.order.model.GetReservateListOutputDto;
import com.cnc.qr.core.order.model.GetReservateOutputDto;
import com.cnc.qr.core.order.model.GetTableInfoDto;
import com.cnc.qr.core.order.model.RegistReservateInputDto;
import com.cnc.qr.core.order.service.ReservateService;
import com.github.dozermapper.core.Mapper;

import io.github.jhipster.web.util.PaginationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 予約処理コントローラ.
 */
@RestController
public class StpdReservateInfoController {

    /**
     * 予約サービス.
     */
    @Autowired
    private ReservateService reservateService;

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
     * ユーザ認証情報取得.
     */
    @Autowired
    @Qualifier("AuthenticationManagerImpl")
    AuthenticationManager authenticationManager;

    /**
     * 予約一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 予約一覧情報
     */
    @PostMapping(UrlConstants.STPD_GET_RESERVATE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ResponseEntity<GetReservateListOutputResource> getReservateList(
        @Validated @RequestBody GetReservateListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetReservateListInputDto inputDto = beanMapper
            .map(inputResource, GetReservateListInputDto.class);

        // 予約一覧情報取得サービス処理を実行する
        GetReservateListOutputDto outputDto = reservateService.getReservateList(inputDto,
            PageRequest.of(inputResource.getPage(), inputResource.getPageCount()));

        // インプット情報をDTOにセットする
        GetReservateListOutputResource outputResource = new GetReservateListOutputResource();

        // 予約一覧情報を設定する
        outputResource.setReservateList(outputDto.getReservateList().getContent());

        // 予約総件数
        outputResource.setReservateCount(outputDto.getReservateCount());

        // ページ情報を設定する
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                outputDto.getReservateList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 取消.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_CANCEL_RESERVATE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public CancelReservateOutputResource cancelReservate(
        @Validated @RequestBody CancelReservateInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CancelReservateInputDto inputDto = beanMapper
            .map(inputResource, CancelReservateInputDto.class);
        inputDto.setReservateIdList(inputResource.getReservateIdList());

        // 取消サービス処理を実行する
        reservateService.cancelReservate(inputDto);

        return new CancelReservateOutputResource();
    }

    /**
     * 来店.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_FINISH_RESERVATE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public FinishReservateOutputResource finishReservate(
        @Validated @RequestBody FinishReservateInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        FinishReservateInputDto inputDto = beanMapper
            .map(inputResource, FinishReservateInputDto.class);

        // 来店サービス処理を実行する
        reservateService.finishReservate(inputDto);

        return new FinishReservateOutputResource();
    }

    /**
     * 予約情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 予約情報
     */
    @PostMapping(UrlConstants.STPD_GET_RESERVATE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetReservateOutputResource getReservate(
        @Validated @RequestBody GetReservateInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetReservateInputDto inputDto = beanMapper
            .map(inputResource, GetReservateInputDto.class);

        // 予約情報取得サービス処理を実行する
        GetReservateOutputDto outputDto = reservateService.getReservate(inputDto);

        // インプット情報をDTOにセットする
        GetReservateOutputResource outputResource = beanMapper
            .map(outputDto, GetReservateOutputResource.class);

        return outputResource;
    }

    /**
     * 席一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 席一覧情報
     */
    @PostMapping(UrlConstants.STPD_ALL_TABLE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetAllTableListOutputResource getAllTableList(
        @Validated @RequestBody GetAllTableListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetAllTableListInputDto inputDto = beanMapper
            .map(inputResource, GetAllTableListInputDto.class);

        // 席一覧情報取得サービス処理を実行する
        GetAllTableListOutputDto outputDto = reservateService.getAllTableList(inputDto);

        // インプット情報をDTOにセットする
        GetAllTableListOutputResource outputResource = new GetAllTableListOutputResource();

        // 席一覧情報を設定する
        List<GetTableInfoDto> tableList = new ArrayList<>();
        outputDto.getTableList().forEach(tableInfoDto -> {
            GetTableInfoDto tableInfo = new GetTableInfoDto();
            tableInfo.setAreaId(tableInfoDto.getAreaId());
            tableInfo.setAreaName(tableInfoDto.getAreaName());
            tableInfo.setTableColor(tableInfoDto.getTableColor());
            tableInfo.setTableName(tableInfoDto.getTableName());
            tableInfo.setTableSeatCount(tableInfoDto.getTableSeatCount());
            tableInfo.setUseFlag(tableInfoDto.getUseFlag());
            tableInfo.setTableId(tableInfoDto.getTableId());
            tableList.add(tableInfo);
        });
        outputResource.setTableList(tableList);

        return outputResource;
    }

    /**
     * 予約.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_REGIST_RESERVATE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public RegistReservateOutputResource registReservate(
        @Validated @RequestBody RegistReservateInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistReservateInputDto inputDto = beanMapper
            .map(inputResource, RegistReservateInputDto.class);
        inputDto.setSelectCourseList(inputResource.getSelectCourseList());
        inputDto.setSelectBuffetList(inputResource.getSelectBuffetList());
        inputDto.setSelectTableList(inputResource.getSelectTableList());

        // 予約サービス処理を実行する
        reservateService.registReservate(inputDto);

        return new RegistReservateOutputResource();
    }
}
