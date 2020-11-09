package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗情報検証データ.
 */
@Data
public class StoreIdVerificationInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * パスワード.
     */
    private String password;

}
