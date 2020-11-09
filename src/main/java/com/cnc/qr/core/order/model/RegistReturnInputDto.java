package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 人数変更データ.
 */
@Data
public class RegistReturnInputDto implements Serializable {

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

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 商品個数.
     */
    private Integer returnCount;

    /**
     * 返品原因コード.
     */
    private String returnReasonCode;
}
