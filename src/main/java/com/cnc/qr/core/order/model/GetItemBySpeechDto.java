package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class GetItemBySpeechDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品順.
     */
    private String sortOrder;

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
    private String itemInfo;

    /**
     * 商品欠品フラグ.
     */
    private String itemSelloffFlag;

    /**
     * 商品オプションフラグ.
     */
    private String optionFlag;

    /**
     * 画像パス.
     */
    private String imagePath;

    /**
     * ショート画像パス.
     */
    private String shortImagePath;

    /**
     * 放题フラグ.
     */
    private String buffetFlag;

    /**
     * コースフラグ.
     */
    private String courseFlag;

    /**
     * 商品数量.
     */
    private String itemCount;

    /**
     * 商品カテゴリーID.
     */
    private String itemCategoryId;

}
