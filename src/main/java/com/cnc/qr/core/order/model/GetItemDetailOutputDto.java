package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品詳細情報取得検索結果.
 */
@Data
public class GetItemDetailOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * 商品実際注文数量.
     */
    private Integer itemRealCount;

    /**
     * 商品備考.
     */
    private String itemInfo;

    /**
     * 商品オプションリスト.
     */
    private List<ItemDetailOptionTypeDto> optionTypeList;
}
