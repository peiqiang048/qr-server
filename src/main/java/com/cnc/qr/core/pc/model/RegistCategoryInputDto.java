package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class RegistCategoryInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * カテゴリーID.
     */
    private Integer categoryId;
    
    /**
     * カテゴリー名称.
     */
    private String categoryName;
    
    /**
     * グラデーション.
     */
    private Integer gradation;
    
    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> parentCategoryIdList;
}
