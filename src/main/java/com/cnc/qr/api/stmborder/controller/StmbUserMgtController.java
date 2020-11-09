package com.cnc.qr.api.stmborder.controller;

import com.cnc.qr.api.stmborder.resource.RegistPasswordInputResource;
import com.cnc.qr.api.stmborder.resource.RegistPasswordOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.RegistPasswordInputDto;
import com.cnc.qr.core.order.service.OrderUserMgtService;
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
 * アカウント管理コントローラ.
 */
@RestController
public class StmbUserMgtController {

    /**
     * アカウント管理サービス.
     */
    @Autowired
    private OrderUserMgtService userMgtService;

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
     * パスワード登録.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return パスワード登録
     */
    @PostMapping(UrlConstants.STMB_REGIST_PASSWORD_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public RegistPasswordOutputResource changeUserPassword(
        @Validated @RequestBody RegistPasswordInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistPasswordInputDto inputDto = beanMapper
            .map(inputResource, RegistPasswordInputDto.class);

        // アカウント管理サービスを実行する
        userMgtService.registPassword(inputDto);

        return new RegistPasswordOutputResource();
    }
}
