package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableSeatDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * 座席数.
     */
    private Integer tableSeatCount;

}
