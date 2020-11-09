package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コース情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * コースID.
     */
    private Integer courseId;

    /**
     * コース单价.
     */
    private BigDecimal coursePrice;

    /**
     * コース数.
     */
    private Integer courseCount;

}
