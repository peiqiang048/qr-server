package com.cnc.qr.api.stpdorder.controller;

import com.cnc.qr.api.stpdorder.resource.GetCallInputResource;
import com.cnc.qr.api.stpdorder.resource.GetCallOutputResource;
import com.cnc.qr.api.stpdorder.resource.ModifyCallStatusInputResource;
import com.cnc.qr.api.stpdorder.resource.ModifyCallStatusOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.GetCallInputDto;
import com.cnc.qr.core.order.model.GetCallOutputDto;
import com.cnc.qr.core.order.model.ModifyCallStatusInputDto;
import com.cnc.qr.core.order.service.CallInfoService;
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
 * 呼出中情報コントローラ.
 */
@RestController
public class StpdCallProcessController {

    /**
     * 呼出中情報サービス.
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
     * 呼出中情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 呼出中情報
     */
    @PostMapping(UrlConstants.STPD_CALL_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetCallOutputResource getCallList(
        @Validated @RequestBody GetCallInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        GetCallInputDto inputDto = beanMapper.map(inputResource, GetCallInputDto.class);

        // サービス処理を実行する
        GetCallOutputDto outputDto = callInfoService.getCallList(inputDto);

        // アウトプット情報を作成する
        GetCallOutputResource outputResource = new GetCallOutputResource();

        // 呼出中情報を設定する
        outputResource.setCallList(outputDto.getCallList());

        return outputResource;
    }

    /**
     * 呼出状態変更.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 変更結果
     */
    @PostMapping(UrlConstants.STPD_MODIFY_CALL_STATUS_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ModifyCallStatusOutputResource modifyCallStatus(
        @Validated @RequestBody ModifyCallStatusInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ModifyCallStatusInputDto inputDto = beanMapper.map(inputResource, ModifyCallStatusInputDto.class);

        // 呼出中サービス処理を実行する
        callInfoService.modifyCallStatus(inputDto);

        return new ModifyCallStatusOutputResource();
    }
}
