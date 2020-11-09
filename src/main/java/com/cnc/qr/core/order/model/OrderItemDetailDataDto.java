package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文商品情報.
 */
@Data
public class OrderItemDetailDataDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

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
     * 差額.
     */
    private BigDecimal diffPrice;

    /**
     * 数量.
     */
    private Integer optionItemCount;

    /**
     * オプション名.
     */
    private String optionName;

    /**
     * 区分.
     */
    private String classification;

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
