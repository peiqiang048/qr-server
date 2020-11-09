package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品情報削除データ.
 */
@Data
public class DeleteCategoryInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryList;
}
