package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 商品情報.
 */
@Data
public class ItemInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * 商品オプション情報リスト.
     */
    private List<ItemOptionTypeDto> optionTypeList;

    /**
     * 商品種類コード.
     */
    private String itemCategoryId;

    /**
     * 商品種類名.
     */
    private String itemCategoryName;

    /**
     * バージョン.
     */
    private Integer version;
}
