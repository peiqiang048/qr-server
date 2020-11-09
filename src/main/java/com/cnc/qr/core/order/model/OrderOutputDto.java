package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文確定処理結果.
 */
@Data
public class OrderOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    private Integer orderId;
    /**
     * 支払区分.
     */
    private String advancePayment;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 店員会計可能フラグ（店員用スマホ）.
     */
    private Integer staffAccountAbleFlag;

}
