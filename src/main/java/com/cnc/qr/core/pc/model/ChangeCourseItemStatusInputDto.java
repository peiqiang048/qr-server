package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * コースステータス更新データ.
 */
@Data
public class ChangeCourseItemStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * コースIDリスト.
     */
    private List<CourseIdDto> courseList;

    /**
     * 销售区分.
     */
    private String salesClassification;
}
