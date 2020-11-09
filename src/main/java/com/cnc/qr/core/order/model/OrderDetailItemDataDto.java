package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文済の商品情報.
 */
@Data
public class OrderDetailItemDataDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付時間.
     */
    private String receptionTime;

    /**
     * 受付番号.
     */
    private Integer receptionNo;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 注文金额.
     */
    private BigDecimal orderAmount;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 値引き値.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 値引き率.
     */
    private BigDecimal priceDiscountRate;

    /**
     * テイクアウト区分.
     */
    private String takeoutFlag;

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
     * 商品确认状态.
     */
    private String itemSureStatus;

    /**
     * 支払区分.
     */
    private String paymentType;

}
