package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コース名.
     */
    private String courseName;

    /**
     * コース説明.
     */
    private String courseInfo;

    /**
     * コース単位ID.
     */
    private Integer courseUnitId;

    /**
     * コース単価.
     */
    private BigDecimal coursePrice;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * コース大きな画像URL.
     */
    private String courseLargePicUrl;

    /**
     * コースサムネイルURL.
     */
    private String courseSmallPicUrl;
}
