package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会計用伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * ブルートゥース名称.
     */
    private String bluetoothName;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * 接続名称.
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
     * 領収書ラベル.
     */
    private String receiptLabel;

    /**
     * 御中ラベル.
     */
    private String youLabel;

    /**
     * 金ラベル.
     */
    private String moneyLabel;

    /**
     * 円ラベル.
     */
    private String yenLabel;
    /**
     * ＜消費税等　ーー　円を含みます＞.
     */
    private String consumptionAmount;
    /**
     * 但し　書籍代として.
     */
    private String bookFee;

    /**
     * 文言ラベル.
     */
    private String wordingLabel;

    /**
     * 発行日.
     */
    private String issueDate;

    /**
     * 印.
     */
    private String markLabel;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗住所.
     */
    private String storeAddress;

    /**
     * ポスト.
     */
    private String postNumber;

    /**
     * tel.
     */
    private String storePhone;


    /**
     * 支払金額.
     */
    private String paymentAmount;
}
