package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗キッチンIDリスト.
 */
@Data
public class KitchenInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * キチンID.
     */
    private Integer kitchenId;
}
