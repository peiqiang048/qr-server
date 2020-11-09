package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 龍高飯店日報伝票印刷アウト.
 */
@Data
public class RgDailyReportDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

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

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗TEL.
     */
    private String storeTel;

    /**
     * 店舗住所.
     */
    private String storeAdress;

    /**
     * プリンター時間.
     */
    private String printTime;

    /**
     * 商品数.
     */
    private Integer itemCount;

    /**
     * 商品金額.
     */
    private String itemPriceSum;

    /**
     * 割引数.
     */
    private String discountRateCount;

    /**
     * 割引金額.
     */
    private String discountRateAmount;

    /**
     * 値引数.
     */
    private String discountCount;

    /**
     * 値引金額.
     */
    private String discountAmount;


    /**
     * オーダー数.
     */
    private String orderCount;

    /**
     * 人数.
     */
    private String customerCount;

    /**
     * 外税金額.
     */
    private String sotoTaxAmount;

    /**
     * 現金回数.
     */
    private String cashCount;

    /**
     * 現金金額.
     */
    private String cashAmount;

    /**
     * クレジット回数.
     */
    private String creditCount;

    /**
     * クレジット金額.
     */
    private String creditAmount;

    /**
     * 掛け回数.
     */
    private String contributionCount;

    /**
     * 掛け金額.
     */
    private String contributionAmount;

    /**
     * 釣銭準備金回数.
     */
    private String changeReserveCount;

    /**
     * 釣銭準備金金額.
     */
    private String changeReserveAmount;

    /**
     * 現金在高金額.
     */
    private String cashCurrentAmount;

    /**
     * 店内飲食.
     */
    private String eatInItemCount;

    /**
     * 店内飲食金額.
     */
    private String eatInItemAmount;

    /**
     * テイクアウト飲食.
     */
    private String takeOutItemCount;

    /**
     * テイクアウト飲食金額.
     */
    private String takeOutItemAmount;

    /**
     * 外税対象額.
     */
    private String foreignTaxAmount;

    /**
     * 内税対象額.
     */
    private String taxableAmount;

    /**
     * 消費税.
     */
    private String consumptionTax;

    /**
     * 客単価.
     */
    private String averagePrice;

    /**
     * 返品.
     */
    private String returnItemCount;

    /**
     * 返品金額.
     */
    private String returnItemAmount;

    /**
     * 精算回数.
     */
    private String exactCalculation;
}
