package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * ユーザ情報取得検索結果.
 */
@Data
public class UserDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザId.
     */
    private String userId;

    /**
     * ユーザ名.
     */
    private String userName;
}
