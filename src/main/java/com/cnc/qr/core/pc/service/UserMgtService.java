package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeRoleStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeUserPasswordInputDto;
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
import org.springframework.data.domain.Pageable;

/**
 * アカウント管理サービス.
 */
public interface UserMgtService {

    /**
     * ユーザー一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return ユーザー一覧情報
     */
    GetUserListOutputDto getUserList(GetUserListInputDto inputDto, Pageable pageable);

    /**
     * ユーザ情報取得.
     *
     * @param inputDto 取得条件
     * @return ユーザ情報
     */
    GetUserOutputDto getUser(GetUserInputDto inputDto);

    /**
     * ユーザ情報編集.
     *
     * @param inputDto ユーザ情報
     */
    void registerUser(RegisterUserInputDto inputDto);

    /**
     * ユーザ情報削除.
     *
     * @param inputDto ユーザ情報
     */
    void deleteUser(DeleteUserInputDto inputDto);

    /**
     * ユーザパスワードリセット.
     *
     * @param inputDto ユーザ情報
     */
    void resetUserPassword(ResetUserPasswordInputDto inputDto);

    /**
     * 役割一覧情報.
     *
     * @param inputDto 取得条件
     * @return 役割情報
     */
    GetRoleListOutputDto getRoleList(GetRoleListInputDto inputDto, Pageable pageable);

    /**
     * 機能情報取得.
     *
     * @param inputDto 取得条件
     * @return 機能情報
     */
    GetRoleOutputDto getRole(GetRoleInputDto inputDto);

    /**
     * 役割情報編集.
     *
     * @param inputDto 役割情報
     */
    void registerRole(RegisterRoleInputDto inputDto);

    /**
     * 役割情報削除.
     *
     * @param inputDto 役割情報
     */
    void deleteRole(DeleteRoleInputDto inputDto);

    /**
     * 役割ステータス更新.
     *
     * @param inputDto 役割情報
     */
    void changeRoleStatus(ChangeRoleStatusInputDto inputDto);

    /**
     * パスワード更新.
     *
     * @param inputDto 役割情報
     */
    void changeUserPassword(ChangeUserPasswordInputDto inputDto);
}
