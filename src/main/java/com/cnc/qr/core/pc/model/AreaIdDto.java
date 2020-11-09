package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * エリアID情報.
 */
@Data
public class AreaIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * エリアID.
     */
    @NotNull
    private Integer areaId;
}
