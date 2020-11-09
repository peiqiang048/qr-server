package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コース情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * コースID.
     */
    private Integer courseId;

    /**
     * コース名称.
     */
    private String courseName;

}
