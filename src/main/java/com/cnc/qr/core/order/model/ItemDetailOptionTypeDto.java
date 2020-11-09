package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品明細オプション種類情報.
 */
@Data
public class ItemDetailOptionTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプション種類Code.
     */
    private String optionTypeCd;

    /**
     * 商品オプション種類名.
     */
    private String optionTypeName;

    /**
     * 商品オプション種類区分.
     */
    private String classification;

    /**
     * 商品オプション.
     */
    private List<ItemDetailOptionDto> optionList;
}
