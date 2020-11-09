package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文厨房伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenSlipDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 食卓名称.
     */
    private String tableName;
    /**
     * 受付番号.
     */
    private String receptionNo;
    /**
     * 注文時間.
     */
    private String orderTime;
    /**
     * 担当者.
     */
    private String responsibleParty;

    /**
     * 厨房明細伝票(堂口单).
     */
    private KitchenDetailsDto kitchenDetailsDto;

    /**
     * 厨房リスト伝票(厨房总单).
     */
    private KitchenListDto kitchenListDto;
}
