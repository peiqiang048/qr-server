package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * コースIDリスト.
 */
@Data
public class CourseInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コースID.
     */
    private Integer courseId;
}
