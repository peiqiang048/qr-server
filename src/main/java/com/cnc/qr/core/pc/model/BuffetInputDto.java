package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 飲み放題IDリスト.
 */
@Data
public class BuffetInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 飲み放題ID.
     */
    private Integer buffetId;
}
