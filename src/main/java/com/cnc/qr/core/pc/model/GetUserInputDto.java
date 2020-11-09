package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * ユーザ情報取得条件.
 */
@Data
public class GetUserInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザID.
     */
    private Integer userId;
}
