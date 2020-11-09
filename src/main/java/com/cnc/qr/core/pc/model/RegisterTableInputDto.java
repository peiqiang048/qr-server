package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル情報編集データ.
 */
@Data
public class RegisterTableInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 席位数.
     */
    private Integer tableSeatCount;

    /**
     * エリアID.
     */
    private Integer areaId;

    /**
     * 席種類.
     */
    private String tableType;
}
