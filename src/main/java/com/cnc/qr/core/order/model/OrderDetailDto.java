package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文詳細取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付時間.
     */
    private ZonedDateTime receptionTime;

    /**
     * 受付番号.
     */
    private Integer receptionNo;
    /**
     * テイクアウト.
     */
    private String takeoutFlag;
    /**
     * 注文金額.
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
     * オーダーID.
     */
    private Integer orderId;
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
     * 商品名.
     */
    private String itemName;
    /**
     * テーブル名.
     */
    private String tableName;
}
