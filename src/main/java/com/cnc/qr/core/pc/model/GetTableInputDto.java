package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル情報取得条件.
 */
@Data
public class GetTableInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * テーブルID.
     */
    private Integer tableId;
}
