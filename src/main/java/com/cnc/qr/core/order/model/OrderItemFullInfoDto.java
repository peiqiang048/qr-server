package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文済の商品情報.
 */
@Data
public class OrderItemFullInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 支払状態.
     */
    private String payStatus;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

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
    private String itemCount;

    /**
     * オップシュ.
     */
    private String option;
    
    /**
     * 商品区分.
     */
    private String itemClassification;

}
