package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 会計PAD注文商品詳細検索結果.
 */
@Data
public class ItemsDetailDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品個数.
     */
    private Integer itemCount;

    /**
     * 商品価格.
     */
    private BigDecimal itemPrice;

    /**
     * 商品オプション.
     */
    private String itemOption;

    /**
     * 商品区分.
     */
    private String itemClassification;

    /**
     * 商品确认状态.
     */
    private String itemSureStatus;
}
