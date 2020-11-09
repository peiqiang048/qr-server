package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 売上リスト.
 */
@Data
public class OrderSummaryAndOrderIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;


    /**
     * 注文ID.
     */
    private List<Integer> orderIdList;
}
