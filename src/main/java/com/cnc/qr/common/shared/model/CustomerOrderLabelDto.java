package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客用伝票ラベル印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderLabelDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * タイトル.
     */
    private String titleOrderLabel;

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
     * 食卓名称.
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
}
