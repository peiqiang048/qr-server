package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリ伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAccountDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 店舗名称.
     */
    private String storeName;
    /**
     * 店舗住所.
     */
    private String storeAddress;
    /**
     * 店舗電話.
     */
    private String storeTel;
    /**
     * 食卓名称.
     */
    private String tableName;
    /**
     * 人数.
     */
    private String peopleNumber;
    /**
     * 注文時間.
     */
    private String orderTime;
    /**
     * 担当者.
     */
    private String responsibleParty;
    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 普通商品リスト.
     */
    private List<NormallyItemDto> normallyItemList;

    /**
     * セットリス.
     */
    private List<SetItemDto> setItemList;

    /**
     * 返品詳細リスト.
     */
    private List<ReturnItemDto> returnItemList;

    /**
     * 割引パーセント.
     */
    private String discount;

    /**
     * 割引金額.
     */
    private String discountAmount;
    /**
     * 小計金額.
     */
    private String subtotal;
    /**
     * 外税金額.
     */
    private String sotoTax;
    /**
     * 合計金額.
     */
    private String totalAmount;
    /**
     * 軽減税率金額.
     */
    private String reducedTaxRate;
    /**
     * 標準税率金額.
     */
    private String standardTaxRate;
    /**
     * 現金金額.
     */
    private String cash;
    /**
     * お預かり金額.
     */
    private String custody;
    /**
     * お釣り金額.
     */
    private String change;
    /**
     * 支払方式１金額.
     */
    private String paymentMethod1;
    /**
     * 支払方式２金額.
     */
    private String paymentMethod2;
    /**
     * 支払方式３金額.
     */
    private String paymentMethod3;
    /**
     * 支払方式４金額.
     */
    private String paymentMethod4;
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
     * 支払方式１.
     */
    private String paymentMethod1Label;
    /**
     * 支払方式２.
     */
    private String paymentMethod2Label;
    /**
     * 支払方式３.
     */
    private String paymentMethod3Label;
    /**
     * 支払方式４.
     */
    private String paymentMethod4Label;
    /**
     * 個人情報.
     */
    private DeliveryPersonalDto deliveryPersonalDto;
}
