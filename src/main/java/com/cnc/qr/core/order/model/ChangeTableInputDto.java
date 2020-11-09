package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 座席変更データ.
 */
@Data
public class ChangeTableInputDto implements Serializable {

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
     * 移動先座席ID.
     */
    private Integer newTableId;
}
