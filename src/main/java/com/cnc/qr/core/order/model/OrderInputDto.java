package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文確定情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 桌号.
     */
    private Integer tableId;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 注文金额.
     */
    private BigDecimal orderAmount;

    /**
     * 版本.
     */
    private String version;

    /**
     * 認証情報.
     */
    private String authorization;

    /**
     * 商品情報リスト.
     */
    private List<OrderItemsDto> items;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 支払区分.
     */
    private String paymentType;

    /**
     * 放题ID.
     */
    private Integer buffetId;

    /**
     * 要望.
     */
    private String comment;

}
