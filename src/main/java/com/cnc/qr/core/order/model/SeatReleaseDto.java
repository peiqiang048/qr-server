package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 席解除情報.
 */
@Data
@AllArgsConstructor
public class SeatReleaseDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付日時.
     */
    private String receptionTime;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 注文金額.
     */
    private BigDecimal orderAmount;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * 受付ID.
     */
    private String receivablesId;
}
