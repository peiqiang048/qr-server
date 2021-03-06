package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザステータス更新データ.
 */
@Data
public class ChangeItemSortOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * カテゴリーID.
     */
    private Integer categoryId;
    
    /**
     * 商品順番リスト.
     */
    private List<ItemSortOrderDto> itemSortOrderList;
}
