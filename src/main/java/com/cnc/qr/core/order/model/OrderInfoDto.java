package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文情報.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文日付.
     */
    private String orderDateTime;

    /**
     * 受付No.
     */
    private String receivablesNo;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 小計金額.
     */
    private BigDecimal orderSubtotal;

    /**
     * 外税金額.
     */
    private BigDecimal orderSototaxAmount;

    /**
     * 合計金額.
     */
    private BigDecimal orderTotalAmount;

    /**
     * 割引值金額.
     */
    private BigDecimal orderDiscountAmount;

    /**
     * 割引率金額.
     */
    private BigDecimal orderDiscountRate;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 注文確認状態.
     */
    private String itemStatus;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 操作人.
     */
    private String operator;

    /**
     * 飲食区分.
     */
    private String takeoutFlag;

    /**
     * テーブルID.
     */
    private String tableId;

    /**
     * 支払区分.
     */
    private String paymentType;
}
