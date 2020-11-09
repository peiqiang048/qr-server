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
public class CustomerOrderDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
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
     * 普通商品リスト.
     */
    private List<NormallyItemDto> normallyItemList;

    /**
     * セットリス.
     */
    private List<SetItemDto> setItemList;

    /**
     * 返品リスト.
     */
    private List<ReturnItemDto> returnItemDtoList;

    /**
     * 小計金額.
     */
    private String subtotal;
    /**
     * 外税金額.
     */
    private String sotoTax;
    /**
     * 合計.
     */
    private String totalAmount;

}
