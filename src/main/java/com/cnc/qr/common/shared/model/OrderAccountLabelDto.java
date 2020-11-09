package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAccountLabelDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 店舗電話.
     */
    private String sotreTelLabel;

    /**
     * 店舗住所.
     */
    private String storeAddressLabel;
    /**
     * 領収書.
     */
    private String receiptLabel;
    /**
     * 食卓名称.
     */
    private String tableNameLabel;
    /**
     * 人数.
     */
    private String peopleNumberLabel;
    /**
     * 注文時間.
     */
    private String orderTimeLabel;
    /**
     * 受付番号.
     */
    private String receptionNoLabel;
    /**
     * 担当者.
     */
    private String responsiblePartyLabel;
    /**
     * 商品名.
     */
    private String itemNameLabel;
    /**
     * 数量.
     */
    private String quantityLabel;
    /**
     * 合計.
     */
    private String totalLabel;
    /**
     * 割引.
     */
    private String discountLabel;
    /**
     * 値引き.
     */
    private String discountValLabel;
    /**
     * 小計.
     */
    private String subtotalLabel;
    /**
     * 外税.
     */
    private String sotoTaxLabel;
    /**
     * 合計.
     */
    private String totalAmountLabel;
    /**
     * 税率.
     */
    private String taxRateLabel;
    /**
     * 対象.
     */
    private String targetLabel;
    /**
     * 現金.
     */
    private String cashLabel;
    /**
     * お預かり.
     */
    private String custodyLabel;
    /**
     * お釣り.
     */
    private String changeLabel;
    /**
     * 提示メッセージ.
     */
    private String presentMessageLabel;
}
