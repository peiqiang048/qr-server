package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品オプション種類情報取得検索結果.
 */
@Data
public class GetItemOptionTypeOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプション種類情報.
     */
    private List<ItemOptionTypeDto> optionTypeList;
}
