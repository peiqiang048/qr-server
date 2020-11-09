package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * テーブル情報.
 */
@Data
@AllArgsConstructor
public class TableInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * テーブルID.
     */
    private String tableId;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * エリア名.
     */
    private String areaName;

    /**
     * コンストラクタ.
     *
     * @param tableId   テーブルID
     * @param tableName テーブル名
     * @param areaName  エリア名
     */
    public TableInfoDto(Integer tableId, String tableName, String areaName) {
        this.tableId = tableId.toString();
        this.tableName = tableName;
        this.areaName = areaName;
    }
}
