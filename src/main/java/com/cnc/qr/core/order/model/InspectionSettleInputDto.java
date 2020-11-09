package com.cnc.qr.core.order.model;

import com.cnc.qr.common.constants.RegexConstants;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class InspectionSettleInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 店舗ID.
     */
    private String storeId;


    /**
     * 前日繰越金.
     */
    private BigDecimal previousDayTransferredAmount;

    /**
     * 本日増減額.
     */
    private BigDecimal todayFluctuationAmount;

    /**
     * 実際レジ内現金金額.
     */
    private BigDecimal practicalCashRegisterAmount;

    /**
     * 計算上レジ内現金金額.
     */
    private BigDecimal calculationCashRegisterAmount;

    /**
     * 現金差異.
     */
    private BigDecimal cashDifferenceAmount;

    /**
     * 出金.
     */
    private BigDecimal outAmount;

    /**
     * 銀行預入額.
     */
    private BigDecimal bankDepositAmount;
    /**
     * 翌日繰越金.
     */
    private BigDecimal nextDayTransferredAmount;
    /**
     * 本日仕入（原価）.
     */
    private BigDecimal purchasingCost;

    /**
     * 精算区分.
     */
    private String settleType;

}
