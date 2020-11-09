package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class GetItemList implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 状态.
     */
    private String itemStatus;

    /**
     * キチン名.
     */
    private String kitchenName;
}
