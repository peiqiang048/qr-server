package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 席一覧情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTableInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 席ID.
     */
    private Integer tableId;

    /**
     * 席名.
     */
    private String tableName;

    /**
     * 色.
     */
    private String tableColor;

    /**
     * エリアID.
     */
    private Integer areaId;

    /**
     * エリア名.
     */
    private String areaName;

    /**
     * 座席数.
     */
    private Integer tableSeatCount;

    /**
     * 占用フラグ.
     */
    private String useFlag;
}
