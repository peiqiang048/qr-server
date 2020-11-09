package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 龍高飯店日報伝票印刷アウト.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RgOrderSummaryDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;


    /**
     * 顧客人数.
     */
    private Integer customerCount;


    /**
     * 支払額.
     */
    private BigDecimal paymentAmount;


    /**
     * 値引額.
     */
    private BigDecimal priceDiscountAmount;


    /**
     * 値引率.
     */
    private BigDecimal priceDiscountRate;

    /**
     * テイクアウト区分.
     */
    private String takeoutFlag;
    /**
     * 商品ID.
     */
    private Integer itemId;


    /**
     * 商品個数.
     */
    private Integer itemCount;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 商品区分.
     */
    private String itemClassification;

}
