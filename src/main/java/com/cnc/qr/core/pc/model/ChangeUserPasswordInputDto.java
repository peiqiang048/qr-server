package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * ユーザパスワード更新データ.
 */
@Data
public class ChangeUserPasswordInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザーID.
     */
    private Integer userId;

    /**
     * パスワード.
     */
    private String oldPassword;

    /**
     * 新パスワード.
     */
    private String newPassword;

    /**
     * 確認パスワード.
     */
    private String surePassword;
}
