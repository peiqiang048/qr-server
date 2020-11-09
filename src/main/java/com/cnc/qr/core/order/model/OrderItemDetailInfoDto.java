package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文商品情報.
 */
@Data
public class OrderItemDetailInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 注文明細ID.
     */
    private String orderDetailId;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * オップシュ.
     */
    private String itemOption;

    /**
     * 商品区分.
     */
    private String itemClassification;

    /**
     * 商品确认状態.
     */
    private String itemSureStatus;
    
    /**
     * 返品元注文明細ID.
     */
    private String returnOrderDetailId;

}
