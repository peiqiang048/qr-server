package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点検精算伝票ラベル印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSettleLabelDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * タイトル（精算）.
     */
    private String settleLabel;


    /**
     * タイトル（点検）.
     */
    private String inspectionLabel;

    /**
     * No..
     */
    private String noLabel;

    /**
     * 期間.
     */
    private String periodLabel;
    /**
     * 電話番号.
     */
    private String phoneNumberLabel;
    /**
     * 総売上.
     */
    private String totalSalesLabel;

    /**
     * オーダー個数.
     */
    private String orderCountLabel;

    /**
     * オーダー個数単位.
     */
    private String orderUnitLabel;

    /**
     * 純売上.
     */
    private String netSalesLabel;

    /**
     * 顧客人数.
     */
    private String customerCountLabel;

    /**
     * 顧客人数単位.
     */
    private String customerUnitLabel;

    /**
     * 消費税総額.
     */
    private String totalConsumptionTaxLabel;

    /**
     * 売上内訳.
     */
    private String salesDetailLabel;

    /**
     * 売上内訳（10% 標準）.
     */
    private String salesDetailNormalLabel;

    /**
     * 売上内訳（8% 軽減）.
     */
    private String salesDetailReliefLabel;
    /**
     * 消費税内訳.
     */
    private String consumptionTaxDetailLabel;


    /**
     * 消費税内訳（10% 標準）.
     */
    private String consumptionTaxDetailNormalLabel;

    /**
     * 消費税内訳（8% 軽減）.
     */
    private String consumptionTaxDetailReliefLabel;

    /**
     * 支払方法.
     */
    private String payMethodLabel;

    /**
     * 現金.
     */
    private String cashLabel;

    /**
     * 商品券.
     */
    private String giftCertificatesLabel;

    /**
     * クレジットカード.
     */
    private String creditCardLabel;

    /**
     * 割引・割増.
     */
    private String discountSurchargeLabel;

    /**
     * 割引.
     */
    private String discountLabel;

    /**
     * クーポン利用.
     */
    private String couponLabel;

    /**
     * 会計修正.
     */
    private String accountingRevisionLabel;

    /**
     * 差異金額.
     */
    private String differenceAmountLabel;

    /**
     * 会計回数.
     */
    private String numberAmountLabel;

    /**
     * 入出金合計金額.
     */
    private String totalDepositWithdrawalAmountLabel;

    /**
     * 入金金額.
     */
    private String depositAmountLabel;

    /**
     * 出金金額.
     */
    private String withdrawalAmountLabel;

    /**
     * 回.
     */
    private String timesLabel;


}
