package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文商品リスト取得検索結果.
 */
@Data
public class GetOrderItemListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 商品リスト.
     */
    private List<OrderItemDetailInfoDto> itemList;

    /**
     * 支払方式.
     */
    private String accountsType;
    
    /**
     * 支払区分.
     */
    private String paymentDistinction;
}
