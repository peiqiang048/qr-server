package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class CategoryInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品カテゴリーID.
     */
    private String categoryId;

    /**
     * 商品カテゴリー名.
     */
    private String categoryName;

    /**
     * 放题フラグ.
     */
    private String buffetFlag;
}
