package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSettleInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;


    /**
     * 前日繰越金.
     */
    @NotBlank
    private String previousDayTransferredAmount;

    /**
     * 本日増減額.
     */
    @NotBlank
    private String todayFluctuationAmount;

    /**
     * 実際レジ内現金金額.
     */
    @NotBlank
    private String practicalCashRegisterAmount;

    /**
     * 計算上レジ内現金金額.
     */
    @NotBlank
    private String calculationCashRegisterAmount;

    /**
     * 現金差異.
     */
    @NotBlank
    private String cashDifferenceAmount;

    /**
     * 出金.
     */
    private String outAmount;

    /**
     * 銀行預入額.
     */
    private String bankDepositAmount;
    /**
     * 翌日繰越金.
     */
    private String nextDayTransferredAmount;
    /**
     * 本日仕入（原価）.
     */
    private String purchasingCost;

    /**
     * 精算区分.
     */
    @NotBlank
    private String settleType;


}
