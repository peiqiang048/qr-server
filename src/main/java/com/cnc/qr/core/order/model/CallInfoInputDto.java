package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗情報取得条件.
 */
@Data
public class CallInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 桌号.
     */
    private Integer tableId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 区分.
     */
    private String callDis;
}
