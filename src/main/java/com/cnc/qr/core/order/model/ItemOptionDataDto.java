package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品オプション種類情報.
 */
@Data
public class ItemOptionDataDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプション種類ID.
     */
    private String optionTypeCd;

    /**
     * 商品オプション種類名.
     */
    private String optionTypeName;

    /**
     * 商品オプション種類順.
     */
    private String optionTypeSortOrder;

    /**
     * 商品オプション種類区分.
     */
    private String classification;

    /**
     * 商品オプションID.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;

    /**
     * 商品オプション順.
     */
    private String optionSortOrder;

    /**
     * 商品スプレッド.
     */
    private String diffPrice;
}
