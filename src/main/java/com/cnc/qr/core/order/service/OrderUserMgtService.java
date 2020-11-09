package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.RegistPasswordInputDto;

/**
 * アカウント管理サービス.
 */
public interface OrderUserMgtService {

    /**
     * パスワード登録.
     *
     * @param inputDto 取得条件
     */
    void registPassword(RegistPasswordInputDto inputDto);
}
