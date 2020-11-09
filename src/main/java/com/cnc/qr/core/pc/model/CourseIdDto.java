package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品ID情報.
 */
@Data
public class CourseIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * コース商品ID.
     */
    @NotNull
    private Integer courseId;
}
