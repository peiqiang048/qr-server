package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 厨房リスト伝票(厨房总单)伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenListDto implements Serializable {

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
     * 普通商品リスト.
     */
    private List<NormallyItemDto> normallyItemList;

    /**
     * セットリス.
     */
    private List<SetItemDto> setItemList;

    /**
     * 返品詳細.
     */
    private List<ReturnItemDto> returnItemList;

    /**
     * 備考リスト.
     */
    private List<String> commentList;


}
