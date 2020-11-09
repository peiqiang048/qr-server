package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル情報.
 */
@Data
public class GetTableInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;
}
