package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * テーブルアウトプット情報.
 */
@Data
public class InspectionSettleInitOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 前日繰越金.
     */
    private BigDecimal previousDayTransferredAmount;

    /**
     * 本日増減額.
     */
    private BigDecimal todayFluctuationAmount;

    /**
     * 点検精算日.
     */
    private String inspectionSettleDate;
}
