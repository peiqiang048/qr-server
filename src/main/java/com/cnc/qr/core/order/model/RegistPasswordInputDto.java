package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 人数変更データ.
 */
@Data
public class RegistPasswordInputDto implements Serializable {

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
    private String userId;

    /**
     * パスワード.
     */
    private String newPassword;
}
