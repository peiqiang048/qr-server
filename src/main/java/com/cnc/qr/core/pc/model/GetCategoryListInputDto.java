package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetCategoryListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * 言語.
     */
    private String languages;

    /**
     * カテゴリー名.
     */
    private String categoryName;

    /**
     * 親カテゴリーID.
     */
    private Integer parentCategoryId;
}
