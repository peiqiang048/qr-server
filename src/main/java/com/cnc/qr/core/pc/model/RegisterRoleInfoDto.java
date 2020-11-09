package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ役割情報.
 */
@Data
@NoArgsConstructor
public class RegisterRoleInfoDto implements Serializable {

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
