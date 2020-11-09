package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetItemOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品説明.
     */
    private String itemInfo;

    /**
     * 商品単位ID.
     */
    private Integer itemUnitId;

    /**
     * 商品単価.
     */
    private BigDecimal itemPrice;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * キチンID.
     */
    private Integer kitchenId;

    /**
     * 出前区分.
     */
    private String deliveryFlag;

    /**
     * 出前仕方.
     */
    private String cateringTypeFlag;

    /**
     * 商品大きな画像URL.
     */
    private String itemLargePicUrl;

    /**
     * 商品サムネイルURL.
     */
    private String itemSmallPicUrl;

    /**
     * 商品区分.
     */
    private String itemType;

    /**
     * 商品ビデオURL.
     */
    private String itemVideo;
}
