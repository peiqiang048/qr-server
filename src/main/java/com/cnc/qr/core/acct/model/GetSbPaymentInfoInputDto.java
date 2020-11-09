package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * SBペイメント情报取得条件.
 */
@Data
public class GetSbPaymentInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 支払い金額.
     */
    private BigDecimal payAmount;

    /**
     * 支払い方法.
     */
    private String paymentMethodCode;

    /**
     * 認証トークン.
     */
    private String token;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 客番号.
     */
    private String no;

    /**
     * 言語.
     */
    private String languages;
}
