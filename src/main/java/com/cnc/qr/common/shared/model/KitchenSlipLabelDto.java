package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文厨房伝票ラベル情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenSlipLabelDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * タイトル(注文リスト).
     */
    private String titleOrderListLabel;

    /**
     * タイトル(注文明細).
     */
    private String titleOrderDetailsLabel;

    /**
     * 食卓名称.
     */
    private String tableNameLabel;
    /**
     * 注文時間.
     */
    private String orderTimeLabel;

    /**
     * 受付番号.
     */
    private String receptionNoLabel;

    /**
     * キチン.
     */
    private String kitchenLabel;

    /**
     * 担当者.
     */
    private String responsiblePartyLabel;

    /**
     * 備考.
     */
    private String commentLabel;
}
