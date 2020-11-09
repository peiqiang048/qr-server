package com.cnc.qr.core.order.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableIdDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブル名.
     */
    @NotNull
    private Integer tableId;

}
