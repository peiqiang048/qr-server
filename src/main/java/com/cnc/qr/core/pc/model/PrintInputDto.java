package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗プリンターIDリスト.
 */
@Data
public class PrintInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンターID.
     */
    private Integer printId;
}
