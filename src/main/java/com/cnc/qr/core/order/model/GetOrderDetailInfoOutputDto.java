package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文済情報取得検索結果.
 */
@Data
public class GetOrderDetailInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 人数.
     */
    private String customerCount;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 注文金额.
     */
    private String orderAmount;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 小計.
     */
    private BigDecimal subtotal;

    /**
     * 外税.
     */
    private BigDecimal foreignTax;

    /**
     * 合計.
     */
    private BigDecimal total;

    /**
     * 割引额.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 割引率.
     */
    private BigDecimal priceDiscountRate;

    /**
     * 未收.
     */
    private BigDecimal unpay;

    /**
     * 受付No.
     */
    private String receivablesNo;

    /**
     * テーブルID.
     */
    private String tableId;
}
