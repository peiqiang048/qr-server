package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 受付選択可能のテーブル情報取得検索結果.
 */
@Data
public class ReceptionTableDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * テーブル名.
     */
    private String tableName;
    /**
     * 座席数.
     */
    private Integer tableSeatCount;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * オーダー数.
     */
    private Integer orderCount;


    /**
     * 空席数.
     */
    private Integer vacantSeat;
    
    /**
     * 予約数.
     */
    private Integer reservateCount;
}
