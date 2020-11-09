package com.cnc.qr.api.stmborder.controller;

import com.cnc.qr.api.stmborder.resource.GetHomePageInfoInputResource;
import com.cnc.qr.api.stmborder.resource.GetHomePageInfoOutputResource;
import com.cnc.qr.api.stmborder.resource.GetUserListInputResource;
import com.cnc.qr.api.stmborder.resource.GetUserListOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.GetHomePageInfoInputDto;
import com.cnc.qr.core.order.model.GetHomePageInfoOutputDto;
import com.cnc.qr.core.order.model.UserDto;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.github.dozermapper.core.Mapper;
import java.util.List;
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
 * 店舗コントローラ.
 */
@RestController
public class StmbStoreInfoController {

    /**
     * 座席情報取得サービス.
     */
    @Autowired
    private StoreInfoService storeInfoService;

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
     * ユーザ一覧取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return ユーザ一覧情報
     */
    @PostMapping(UrlConstants.STMB_GET_USER_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    public GetUserListOutputResource getUserList(
        @Validated @RequestBody GetUserListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // ユーザリスト
        List<UserDto> userList = storeInfoService.getUser(inputResource.getStoreId());
        GetUserListOutputResource outputResource = new GetUserListOutputResource();
        outputResource.setUserList(userList);

        return outputResource;
    }

    /**
     * トップ画面情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return トップ画面情報
     */
    @PostMapping(UrlConstants.STMB_HOME_PAGE_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public GetHomePageInfoOutputResource getHomePageInfo(
        @Validated @RequestBody GetHomePageInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetHomePageInfoInputDto inputDto = beanMapper
            .map(inputResource, GetHomePageInfoInputDto.class);

        // メニューリスト取得
        GetHomePageInfoOutputDto outputDto = storeInfoService.getHomePageInfo(inputDto);

        // インプット情報をDTOにセットする
        GetHomePageInfoOutputResource outputResource = new GetHomePageInfoOutputResource();
        outputResource.setStoreName(outputDto.getStoreName());
        outputResource.setStoreTime(outputDto.getStoreTime());
        outputResource.setStaffCheckFlag(outputDto.getStaffCheckFlag());
        outputResource.setLanguageList(outputDto.getLanguageList());
        outputResource.setUnCofimCount(outputDto.getUnCofimCount().toString());
        return outputResource;
    }
}
