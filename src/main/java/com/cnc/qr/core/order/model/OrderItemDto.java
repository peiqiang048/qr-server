package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 注文商品情報.
 */
@Data
@AllArgsConstructor
public class OrderItemDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テイクアウト区分.
     */
    private String takeoutFlag;

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 商品数.
     */
    private Integer itemCount;

    /**
     * 商品区分.
     */
    private String itemClassification;

}
