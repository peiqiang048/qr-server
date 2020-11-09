package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗情報取得検索結果.
 */
@Data
public class StoreDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 店舗名称.
     */
    private String storeName;

    /**
     * 営業開始時間.
     */
    private String openStoreTime;

    /**
     * 営業締め時間.
     */
    private String closeStoreTime;

    /**
     * 店舗電話.
     */
    private String tel;

    /**
     * 郵便番号.
     */
    private String postNumber;

    /**
     * 支払方式.
     */
    private String paymentTerminal;
}
