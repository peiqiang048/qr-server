package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文一覧情報取得条件.
 */
@Data
public class GetReservateListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 顧客名前.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String telNumber;

    /**
     * 状態.
     */
    private String status;

    /**
     * 開始日付.
     */
    private String reservateTimeFrom;

    /**
     * 終了日付.
     */
    private String reservateTimeTo;
}
