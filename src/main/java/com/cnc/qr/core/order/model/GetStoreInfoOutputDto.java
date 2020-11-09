package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗情報取得検索結果.
 */
@Data
public class GetStoreInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 営業時間.
     */
    private String storeTime;
}
