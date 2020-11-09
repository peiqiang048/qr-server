package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザパスワードリセットデータ.
 */
@Data
public class ResetUserPasswordInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザーIDリスト.
     */
    private List<UserIdDto> userList;
}
