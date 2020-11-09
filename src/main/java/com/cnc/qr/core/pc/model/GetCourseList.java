package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class GetCourseList implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * コースID.
     */
    private String courseId;

    /**
     * コース名.
     */
    private String courseName;

    /**
     * コース金額.
     */
    private BigDecimal coursePrice;

    /**
     * 状态.
     */
    private String courseStatus;

    /**
     * 単位.
     */
    private String courseUnitName;
}
