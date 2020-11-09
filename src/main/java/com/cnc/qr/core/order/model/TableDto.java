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
public class TableDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * テーブルエリア名.
     */
    private String areaName;

    /**
     * 座席数.
     */
    private Integer tableSeatCount;

}
