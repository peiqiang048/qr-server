package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class DeleteItemInputDto implements Serializable {

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
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 注文明細ID.
     */
    private Integer orderDetailId;
}
