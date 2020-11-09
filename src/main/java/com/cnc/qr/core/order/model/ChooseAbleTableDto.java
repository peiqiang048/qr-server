package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 受付選択可能のテーブル情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseAbleTableDto implements Serializable {

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
     * 顧客人数.
     */
    private Integer tableSeatCount;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;


    /**
     * 注文人数.
     */
    private Integer customerCount;
    
    /**
     * 予約数.
     */
    private Integer reservateCount;
}
