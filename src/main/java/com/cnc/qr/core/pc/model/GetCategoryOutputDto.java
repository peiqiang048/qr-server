package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリー情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * カテゴリー名.
     */
    private String categoryName;
    
    /**
     * グラデーション.
     */
    private Integer gradation;
}
