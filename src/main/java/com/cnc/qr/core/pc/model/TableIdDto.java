package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * テーブルID情報.
 */
@Data
public class TableIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブルID.
     */
    @NotNull
    private Integer tableId;
}
