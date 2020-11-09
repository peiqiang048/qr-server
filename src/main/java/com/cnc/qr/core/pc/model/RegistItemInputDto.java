package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class RegistItemInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 商品名称.
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
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

    /**
     * 商品単価.
     */
    private BigDecimal itemPrice;

    /**
     * 商品税ID.
     */
    private Integer itemTaxId;

    /**
     * 商品キチンID.
     */
    private Integer itemKitchenId;

    /**
     * 出前区分.
     */
    private String deliveryFlag;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 商品大きな画像URL.
     */
    private String itemLargePicUrl;

    /**
     * 商品サムネイルURL.
     */
    private String itemSmallPicUrl;

    /**
     * 商品選択したオプション.
     */
    private List<RegistItemOptionDto> itemOptionList;

    /**
     * 商品ビデオURL.
     */
    private String itemVideo;
}
