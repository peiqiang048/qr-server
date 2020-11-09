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
public class KitchenItemDto implements Serializable {

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
     * キチン名.
     */
    private String kitchenName;

    /**
     * 商品区分.
     */
    private String itemClassification;

    /**
     * 普通商品.
     */
    private NormallyItemDto normallyItemDto;

    /**
     * セットリス.
     */
    private SetItemDto setItemDto;

    /**
     * 返品詳細.
     */
    private ReturnItemDto returnItemDto;
}
