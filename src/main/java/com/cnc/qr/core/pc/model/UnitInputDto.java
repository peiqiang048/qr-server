package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 单位IDリスト.
 */
@Data
public class UnitInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 单位ID.
     */
    private Integer unitId;
}
