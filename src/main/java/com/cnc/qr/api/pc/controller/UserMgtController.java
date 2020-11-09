package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.ChangeRoleStatusInputResource;
import com.cnc.qr.api.pc.resource.ChangeRoleStatusOutputResource;
import com.cnc.qr.api.pc.resource.ChangeUserPasswordInputResource;
import com.cnc.qr.api.pc.resource.ChangeUserPasswordOutputResource;
//import com.cnc.qr.api.pc.resource.ChangeUserStatusInputResource;
//import com.cnc.qr.api.pc.resource.ChangeUserStatusOutputResource;
import com.cnc.qr.api.pc.resource.DeleteRoleInputResource;
import com.cnc.qr.api.pc.resource.DeleteRoleOutputResource;
import com.cnc.qr.api.pc.resource.DeleteUserInputResource;
import com.cnc.qr.api.pc.resource.DeleteUserOutputResource;
import com.cnc.qr.api.pc.resource.GetRoleInputResource;
import com.cnc.qr.api.pc.resource.GetRoleListInputResource;
import com.cnc.qr.api.pc.resource.GetRoleListOutputResource;
import com.cnc.qr.api.pc.resource.GetRoleOutputResource;
import com.cnc.qr.api.pc.resource.GetUserInputResource;
import com.cnc.qr.api.pc.resource.GetUserListInputResource;
import com.cnc.qr.api.pc.resource.GetUserListOutputResource;
import com.cnc.qr.api.pc.resource.GetUserOutputResource;
import com.cnc.qr.api.pc.resource.RegisterRoleInputResource;
import com.cnc.qr.api.pc.resource.RegisterRoleOutputResource;
import com.cnc.qr.api.pc.resource.RegisterUserInputResource;
import com.cnc.qr.api.pc.resource.RegisterUserOutputResource;
import com.cnc.qr.api.pc.resource.ResetUserPasswordInputResource;
import com.cnc.qr.api.pc.resource.ResetUserPasswordOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.core.pc.model.ChangeRoleStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeUserPasswordInputDto;
//import com.cnc.qr.core.pc.model.ChangeUserStatusInputDto;
import com.cnc.qr.core.pc.model.DeleteRoleInputDto;
import com.cnc.qr.core.pc.model.DeleteUserInputDto;
import com.cnc.qr.core.pc.model.GetRoleInputDto;
import com.cnc.qr.core.pc.model.GetRoleListInputDto;
import com.cnc.qr.core.pc.model.GetRoleListOutputDto;
import com.cnc.qr.core.pc.model.GetRoleOutputDto;
import com.cnc.qr.core.pc.model.GetUserInputDto;
import com.cnc.qr.core.pc.model.GetUserListInputDto;
import com.cnc.qr.core.pc.model.GetUserListOutputDto;
import com.cnc.qr.core.pc.model.GetUserOutputDto;
import com.cnc.qr.core.pc.model.RegisterRoleInputDto;
import com.cnc.qr.core.pc.model.RegisterUserInputDto;
import com.cnc.qr.core.pc.model.ResetUserPasswordInputDto;
import com.cnc.qr.core.pc.service.UserMgtService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * アカウント管理コントローラ.
 */
@RestController
public class UserMgtController {

    /**
     * アカウント管理サービス.
     */
    @Autowired
    private UserMgtService userMgtService;

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
     * ユーザー一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return ユーザー一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_USER_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public ResponseEntity<GetUserListOutputResource> getUserList(
        @Validated @RequestBody GetUserListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetUserListInputDto inputDto = beanMapper.map(inputResource, GetUserListInputDto.class);

        // アカウント管理サービスを実行する
        GetUserListOutputDto outDto = userMgtService.getUserList(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetUserListOutputResource outputResource = new GetUserListOutputResource();
        outputResource.setUserList(outDto.getUserList().getContent());

        outputResource.setTotalCount(outDto.getTotalCount());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                outDto.getUserList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * ユーザ情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return ユーザ情報
     */
    @PostMapping(UrlConstants.PC_GET_USER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public GetUserOutputResource getUser(
        @Validated @RequestBody GetUserInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetUserInputDto inputDto = beanMapper.map(inputResource, GetUserInputDto.class);

        // アカウント管理サービスを実行する
        GetUserOutputDto outDto = userMgtService.getUser(inputDto);

        // インプット情報をDTOにセットする
        GetUserOutputResource outputResource = new GetUserOutputResource();
        outputResource.setLoginId(outDto.getLoginId());
        outputResource.setUserName(outDto.getUserName());
        outputResource.setClassification(outDto.getClassification());
        outputResource.setCheckedRoleList(outDto.getCheckedRoleList());
        outputResource.setClassificationList(outDto.getClassificationList());
        outputResource.setRoleList(outDto.getRoleList());

        return outputResource;
    }

    /**
     * ユーザ情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_REGIST_USER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public RegisterUserOutputResource registerUser(
        @Validated @RequestBody RegisterUserInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegisterUserInputDto inputDto = beanMapper.map(inputResource, RegisterUserInputDto.class);
        inputDto.setCheckedRoleList(inputResource.getCheckedRoleList());

        // アカウント管理サービスを実行する
        userMgtService.registerUser(inputDto);

        return new RegisterUserOutputResource();
    }

    /**
     * ユーザ情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public DeleteUserOutputResource delUser(
        @Validated @RequestBody DeleteUserInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteUserInputDto inputDto = beanMapper.map(inputResource, DeleteUserInputDto.class);
        inputDto.setUserList(inputResource.getUserList());

        // アカウント管理サービスを実行する
        userMgtService.deleteUser(inputDto);

        return new DeleteUserOutputResource();
    }


    /**
     * ユーザパスワードリセット.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_RESET_USER_PASSWORD)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public ResetUserPasswordOutputResource changeUserStatus(
        @Validated @RequestBody ResetUserPasswordInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ResetUserPasswordInputDto inputDto = beanMapper
            .map(inputResource, ResetUserPasswordInputDto.class);
        inputDto.setUserList(inputResource.getUserList());

        // アカウント管理サービスを実行する
        userMgtService.resetUserPassword(inputDto);

        return new ResetUserPasswordOutputResource();
    }

    /**
     * 役割一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 役割一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_ROLE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public GetRoleListOutputResource getRoleList(
        @Validated @RequestBody GetRoleListInputResource inputResource, BindingResult result,
        @PageableDefault(size = 8) Pageable pageable) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetRoleListInputDto inputDto = beanMapper.map(inputResource, GetRoleListInputDto.class);

        // アカウント管理サービスを実行する
        GetRoleListOutputDto outDto = userMgtService.getRoleList(inputDto, pageable);

        // インプット情報をDTOにセットする
        GetRoleListOutputResource outputResource = new GetRoleListOutputResource();
        outputResource.setRoleList(outDto.getRoleList().getContent());

        outputResource.setTotalCount(outDto.getTotalCount());

        return outputResource;
    }

    /**
     * 役割情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 役割情報
     */
    @PostMapping(UrlConstants.PC_GET_ROLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public GetRoleOutputResource getRole(
        @Validated @RequestBody GetRoleInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetRoleInputDto inputDto = beanMapper.map(inputResource, GetRoleInputDto.class);

        // アカウント管理サービスを実行する
        GetRoleOutputDto outDto = userMgtService.getRole(inputDto);

        // インプット情報をDTOにセットする
        GetRoleOutputResource outputResource = new GetRoleOutputResource();
        outputResource.setRoleName(outDto.getRoleName());
        outputResource.setCheckedModelList(outDto.getCheckedModelList());
        outputResource.setModelList(outDto.getModelList());

        return outputResource;
    }

    /**
     * 役割情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_REGIST_ROLE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public RegisterRoleOutputResource registerRole(
        @Validated @RequestBody RegisterRoleInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegisterRoleInputDto inputDto = beanMapper.map(inputResource, RegisterRoleInputDto.class);
        inputDto.setCheckedModelList(inputResource.getCheckedModelList());

        // アカウント管理サービスを実行する
        userMgtService.registerRole(inputDto);

        return new RegisterRoleOutputResource();
    }

    /**
     * 役割情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_ROLE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public DeleteRoleOutputResource registerRole(
        @Validated @RequestBody DeleteRoleInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteRoleInputDto inputDto = beanMapper.map(inputResource, DeleteRoleInputDto.class);
        inputDto.setRoleList(inputResource.getRoleList());

        // アカウント管理サービスを実行する
        userMgtService.deleteRole(inputDto);

        return new DeleteRoleOutputResource();
    }

    /**
     * 役割ステータス更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_ROLE_STATUS)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public ChangeRoleStatusOutputResource changeRoleStatus(
        @Validated @RequestBody ChangeRoleStatusInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeRoleStatusInputDto inputDto = beanMapper
            .map(inputResource, ChangeRoleStatusInputDto.class);
        inputDto.setRoleList(inputResource.getRoleList());

        // アカウント管理サービスを実行する
        userMgtService.changeRoleStatus(inputDto);

        return new ChangeRoleStatusOutputResource();
    }

    /**
     * ユーザパスワード更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_USER_PASSWORD)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER_MAINTENANCE + "\")")
    public ChangeUserPasswordOutputResource changeUserPassword(
        @Validated @RequestBody ChangeUserPasswordInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // パスワード一致チェック
        if (!Objects.equals(inputResource.getNewPassword(), inputResource.getSurePassword())) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.059", (Object) null));
        }

        // インプット情報をDTOにセットする
        ChangeUserPasswordInputDto inputDto = beanMapper
            .map(inputResource, ChangeUserPasswordInputDto.class);

        // アカウント管理サービスを実行する
        userMgtService.changeUserPassword(inputDto);

        return new ChangeUserPasswordOutputResource();
    }
}
