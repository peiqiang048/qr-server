package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetCourseListInputDto implements Serializable {

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
     * 状態.
     */
    private String courseStatus;

    /**
     * コース名.
     */
    private String courseName;

    /**
     * カテゴリーID.
     */
    private Integer categoryId;
}
