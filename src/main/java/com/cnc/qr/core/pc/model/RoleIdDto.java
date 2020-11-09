package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 役割ID情報.
 */
@Data
public class RoleIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 役割ID.
     */
    @NotNull
    private Integer roleId;
}
