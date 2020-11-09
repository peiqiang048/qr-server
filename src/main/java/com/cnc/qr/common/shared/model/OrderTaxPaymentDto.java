package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点検精算伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTaxPaymentDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * オーダーサマリID.
     */
    private String orderSummaryId;

    /**
     * 値引金額.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 割引金額.
     */
    private BigDecimal priceDiscountRate;

    /**
     * 支払金額.
     */
    private BigDecimal orderPaymentAmount;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 支払種類より支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 支払方法.
     */
    private String paymentMethodCode;

    /**
     * 消費税金額.
     */
    private BigDecimal consumptionAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 外税（標準）金額.
     */
    private BigDecimal foreignNormalAmount;

    /**
     * 外税（標準）対象金額.
     */
    private BigDecimal foreignNormalObjectAmount;

    /**
     * 外税（軽減）金額.
     */
    private BigDecimal foreignReliefAmount;
    /**
     * 外税（軽減）対象金額.
     */
    private BigDecimal foreignReliefObjectAmount;

    /**
     * 内税金額.
     */
    private BigDecimal includedTax;

    /**
     * 内税（標準）金額.
     */
    private BigDecimal includedNormalAmount;
    /**
     * 内税（標準）対象金額.
     */
    private BigDecimal includedNormalObjectAmount;

    /**
     * 内税（軽減）金額.
     */
    private BigDecimal includedReliefAmount;

    /**
     * 内税（軽減）対象金額.
     */
    private BigDecimal includedReliefObjectAmount;
}
