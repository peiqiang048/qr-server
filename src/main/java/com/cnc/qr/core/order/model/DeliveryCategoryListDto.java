package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前商品カテゴリー情報取得検索結果.
 */
@Data
public class DeliveryCategoryListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品カテゴリーID.
     */
    private String itemCategoryId;

    /**
     * 商品カテゴリー名.
     */
    private String itemCategoryName;
}
