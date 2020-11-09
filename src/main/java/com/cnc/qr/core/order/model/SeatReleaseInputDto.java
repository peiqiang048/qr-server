package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 席解除データ.
 */
@Data
public class SeatReleaseInputDto implements Serializable {

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

    /**
     * テーブルID.
     */
    private Integer tableId;
}
