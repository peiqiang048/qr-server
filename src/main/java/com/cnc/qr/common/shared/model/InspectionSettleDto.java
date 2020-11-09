package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客用伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSettleDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 店舗名称.
     */
    private String storeName;
    /**
     * No..
     */
    private String no;
    /**
     * 点検精算開始時間.
     */
    private String inspectionSettleStart;

    /**
     * 点検精算終了時間.
     */
    private String inspectionSettleEnd;

    /**
     * 電話番号.
     */
    private String phoneNumber;
    /**
     * 総売上.
     */
    private String totalSales;

    /**
     * オーダー個数.
     */
    private String orderCount;

    /**
     * 純売上.
     */
    private String netSales;


    /**
     * オーダー人数.
     */
    private String orderCustomerCount;

    /**
     * 消費税総額.
     */
    private String totalConsumptionTax;

    /**
     * 売上内訳（10% 標準）.
     */
    private String salesDetailNormalAmount;

    /**
     * 売上内訳（8% 軽減）.
     */
    private String salesDetailReliefAmount;

    /**
     * 消費税内訳（10% 標準）.
     */
    private String consumptionTaxDetailNormalAmount;

    /**
     * 消費税内訳（8% 軽減）.
     */
    private String consumptionTaxDetailReliefAmount;

    /**
     * 現金決済金額.
     */
    private String cashAmount;

    /**
     * 現金決済回数.
     */
    private String cashCount;

    /**
     * 商品券.
     */
    private String giftCertificates;

    /**
     * クレジットカード支払金額.
     */
    private String creditCardAmount;

    /**
     * クレジットカード支払回数.
     */
    private String creditCardCount;

    /**
     * 割引金額.
     */
    private String discountAmount;
    /**
     * 割引回数.
     */
    private String discountCount;

    /**
     * クーポン利用.
     */
    private String couponAmount;
    /**
     * クーポン利用.
     */
    private String couponCount;


    /**
     * 会計修正回数.
     */
    private String accountingRevisionCount;

    /**
     * 差異金額.
     */
    private String differenceAmount;

    /**
     * 会計回数.
     */
    private String numberAmountCount;

    /**
     * 入出金合計金額.
     */
    private String totalDepositWithdrawalAmount;

    /**
     * 入金金額.
     */
    private String depositAmount;

    /**
     * 入金回数.
     */
    private String depositAmountCount;

    /**
     * 出金金額.
     */
    private String withdrawalAmount;

    /**
     * 出金回数.
     */
    private String withdrawalAmountCount;
    /**
     * ブルートゥース名称.
     */
    private String bluetoothName;
    /**
     * プリンターIP.
     */
    private String printIp;
    /**
     * 接続方式区分.
     */
    private String connectionMethod;
    /**
     * プリンタブランドCD.
     */
    private String brandCode;
    /**
     * 伝票幅CD.
     */
    private String printSize;
}
