package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class ItemDetailInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品順.
     */
    private String itemSortNo;

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
     * 商品単位名.
     */
    private String itemUnitName;

    /**
     * 商品説明.
     */
    private String itemDescription;

    /**
     * 商品欠品フラグ.
     */
    private String itemSoldOutFlag;

    /**
     * 商品オプションフラグ.
     */
    private String optionFlag;
}
