package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル情報.
 */
@Data
public class TableInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 桌号.
     */
    private Integer tableId;
}
