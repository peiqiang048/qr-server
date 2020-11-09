package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * テーブルアウトプット情報.
 */
@Data
public class GetTableInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 桌号.
     */
    private Integer tableId;

    /**
     * テーブル名.
     */
    private String tableName;
}
