package com.cnc.qr.api.csmborder.controller;

import com.cnc.qr.api.csmborder.resource.CallInfoInputResource;
import com.cnc.qr.api.csmborder.resource.CallInfoOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.push.FcmPush;
import com.cnc.qr.core.order.model.CallInfoInputDto;
import com.cnc.qr.core.order.model.CallTebleInfoDto;
import com.cnc.qr.core.order.service.CallInfoService;
import com.github.dozermapper.core.Mapper;
import java.util.Locale;
import java.util.Objects;
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
 * 店員呼出コントローラ.
 */
@RestController
public class CsmbCallProcessController {

    /**
     * 店舗媒体情報取得サービ.
     */
    @Autowired
    private CallInfoService callInfoService;

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
     * プッシュ通知.
     */
    @Autowired
    private FcmPush fcmPush;


    /**
     * 店員呼出.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.CSMB_CALL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CallInfoOutputResource callInfo(
        @Validated @RequestBody CallInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        CallInfoInputDto inputDto = beanMapper.map(inputResource, CallInfoInputDto.class);

        //  店員呼出サービス処理を実行する
        CallTebleInfoDto callTebleInfoDto = callInfoService.callInfo(inputDto);

        if (!Objects
            .isNull(callTebleInfoDto.getTableName())) {
            // プッシュ 会計PAD
            String message = String.format("テーブル %s 呼出があります。注文番号：%s",
                callTebleInfoDto.getTableName(),
                String.format("%04d", callTebleInfoDto.getReceptionNo()));
            fcmPush.getPushToken(inputDto.getStoreId(), message);
        }

        return new CallInfoOutputResource();
    }
}
