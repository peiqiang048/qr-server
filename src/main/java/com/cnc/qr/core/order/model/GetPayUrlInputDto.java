package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetPayUrlInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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
    private String payAmount;

    /**
     * 支払い方法.
     */
    private String paymentMethodCode;

    /**
     * 最大明細ID.
     */
    private Integer maxDetailId;

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
    
    /**
     * maxId.
     */
    private String maxId;
}
