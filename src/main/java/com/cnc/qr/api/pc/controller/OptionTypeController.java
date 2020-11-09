package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.ChangeOptionTypeSortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeOptionTypeSortOrderOutputResource;
import com.cnc.qr.api.pc.resource.DeleteOptionTypeInputResource;
import com.cnc.qr.api.pc.resource.DeleteOptionTypeOutputResource;
import com.cnc.qr.api.pc.resource.GetOptionTypeInputResource;
import com.cnc.qr.api.pc.resource.GetOptionTypeListInputResource;
import com.cnc.qr.api.pc.resource.GetOptionTypeListOutputResource;
import com.cnc.qr.api.pc.resource.GetOptionTypeOutputResource;
import com.cnc.qr.api.pc.resource.RegisterOptionTypeInputResource;
import com.cnc.qr.api.pc.resource.RegisterOptionTypeOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.ChangeOptionTypeSortOrderInputDto;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import com.cnc.qr.core.pc.model.DeleteOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeListInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeOutputDto;
import com.cnc.qr.core.pc.model.RegisterOptionTypeInputDto;
import com.cnc.qr.core.pc.service.OptionTypeService;
import com.github.dozermapper.core.Mapper;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
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
 * カテゴリー情報コントローラ.
 */
@RestController
public class OptionTypeController {

    /**
     * オプション種類情報取得サービス.
     */
    @Autowired
    private OptionTypeService optionTypeService;

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
     * オプション種類情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return オプション種類情報
     */
    @PostMapping(UrlConstants.PC_GET_OPTION_TYPE_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetOptionTypeListOutputResource getOptionTypList(
        @Validated @RequestBody GetOptionTypeListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOptionTypeListInputDto inputDto = beanMapper
            .map(inputResource, GetOptionTypeListInputDto.class);

        // サービス処理を実行する
        GetOptionTypeListOutputDto outputDto = optionTypeService.getOptionTypeList(inputDto);

        // アウトプット情報を作成する
        GetOptionTypeListOutputResource outputResource = new GetOptionTypeListOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setOptionTypeList(outputDto.getOptionTypeList());

        return outputResource;
    }

    /**
     * オプション種類明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return オプション種類情報
     */
    @PostMapping(UrlConstants.PC_GET_OPTION_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetOptionTypeOutputResource getOptionType(
        @Validated @RequestBody GetOptionTypeInputResource inputResource, BindingResult result,
        HttpServletRequest request) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOptionTypeInputDto inputDto = beanMapper.map(inputResource, GetOptionTypeInputDto.class);

        // アウトプット情報を作成する
        GetOptionTypeOutputResource outputResource = new GetOptionTypeOutputResource();

        if (null != inputDto.getOptionTypeCd() && StringUtils
            .isNotBlank(inputDto.getOptionTypeCd())) {

            // サービス処理を実行する
            GetOptionTypeOutputDto outputDto = optionTypeService.getOptionTypeInfo(inputDto);

            beanMapper.map(outputDto, outputResource);
        }

        // カテゴリーID情報取得
        List<ClassificationInfoDto> classificationList = optionTypeService
            .getClassificationList(inputDto);
        outputResource.setClassificationList(classificationList);

        return outputResource;
    }

    /**
     * オプション種類情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_REGIST_OPTION_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public RegisterOptionTypeOutputResource registerUser(
        @Validated @RequestBody RegisterOptionTypeInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegisterOptionTypeInputDto inputDto = beanMapper
            .map(inputResource, RegisterOptionTypeInputDto.class);

        // アカウント管理サービスを実行する
        optionTypeService.registerOptionType(inputDto);

        return new RegisterOptionTypeOutputResource();
    }

    /**
     * オプション種類情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_OPTION_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public DeleteOptionTypeOutputResource deleteOptionType(
        @Validated @RequestBody DeleteOptionTypeInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteOptionTypeInputDto inputDto = beanMapper
            .map(inputResource, DeleteOptionTypeInputDto.class);
        inputDto.setOptionTypeList(inputResource.getOptionTypeList());

        // アカウント管理サービスを実行する
        optionTypeService.deleteOptionType(inputDto);

        return new DeleteOptionTypeOutputResource();
    }

    /**
     * オプション種類順番編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_OPTION_TYPE_SORT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeOptionTypeSortOrderOutputResource changeOptionTypeSortOrder(
        @Validated @RequestBody ChangeOptionTypeSortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeOptionTypeSortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeOptionTypeSortOrderInputDto.class);
        inputDto.setOptionTypeSortOrderList(inputResource.getOptionTypeSortOrderList());

        // 商品順番編集サービス処理を実行する
        optionTypeService.changeOptionTypeSortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeOptionTypeSortOrderOutputResource();
    }
}
