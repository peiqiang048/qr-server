package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文一覧情報取得条件.
 */
@Data
public class GetOrderListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付No.
     */
    private Integer receivablesNo;

    /**
     * 開始日付.
     */
    private String orderDateStart;

    /**
     * 終了日付.
     */
    private String orderDateEnd;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 飲食区分.
     */
    private String takeoutFlag;
}
