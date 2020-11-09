package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文確認の商品情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 商品数量.
     */
    private String itemCount;

    /**
     * 商オップシュ金額.
     */
    private BigDecimal attributeTotalMoney;

    /**
     * 商オップシュリスト.
     */
    private List<OrderItemOptionDto> option;

}
