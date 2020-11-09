package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 呼出状態変更データ.
 */
@Data
public class ModifyCallStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * ユーザID.
     */
    private String userId;
}
