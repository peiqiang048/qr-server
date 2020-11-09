package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前注文一覧情報取得条件.
 */
@Data
public class GetDeliveryOrderListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 開始日付.
     */
    private String deliveryOrderTimeFrom;

    /**
     * 終了日付.
     */
    private String deliveryOrderTimeTo;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 状態.
     */
    private String status;
}
