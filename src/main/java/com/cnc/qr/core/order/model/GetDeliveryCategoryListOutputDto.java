package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class GetDeliveryCategoryListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品カテゴリー情報.
     */
    private List<DeliveryCategoryListDto> itemCategoryList;
}
