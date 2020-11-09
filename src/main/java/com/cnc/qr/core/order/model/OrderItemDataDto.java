package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文済の商品情報.
 */
@Data
public class OrderItemDataDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 注文金额.
     */
    private BigDecimal orderAmount;

    /**
     * テイクアウト区分.
     */
    private String takeoutFlag;

    /**
     * 支払状態.
     */
    private String payStatus;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

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
    private String itemCount;

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
     * 支払区分.
     */
    private String paymentType;
}
