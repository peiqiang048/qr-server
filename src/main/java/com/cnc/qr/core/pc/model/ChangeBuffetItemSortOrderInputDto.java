package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 飲み放題商品順番管理.
 */
@Data
public class ChangeBuffetItemSortOrderInputDto implements Serializable {

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
     * 飲み放題商品順番リスト.
     */
    private List<ItemSortOrderDto> buffetItemSortOrderList;
}
