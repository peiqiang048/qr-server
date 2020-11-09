package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * コース選択リスト.
 */
@Data
public class SelectCourseDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コースID.
     */
    private Integer courseId;

    /**
     * コース個数.
     */
    private Integer courseCount;

}
