package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * ユーザーID情報.
 */
@Data
public class UserIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザーID.
     */
    @NotNull
    private Integer userId;
}
