package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文済情報取得検索結果.
 */
@Data
public class GetOrderInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 小計金额.
     */
    private BigDecimal orderAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 支払済金額.
     */
    private BigDecimal paidPrice;

    /**
     * 未支払金額.
     */
    private BigDecimal unpaidPrice;

    /**
     * 支払済商品リスト.
     */
    private List<OrderItemInfoDto> paidItemList;

    /**
     * 未支払商品リスト.
     */
    private List<OrderItemInfoDto> unpaidItemList;
}
