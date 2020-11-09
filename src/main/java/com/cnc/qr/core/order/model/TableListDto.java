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
public class TableListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * テーブル名.
     */
    private String tableName;
}
