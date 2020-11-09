package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * テーブル情報.
 */
@Data
public class TableInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブルID.
     */
    private String tableId;

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
     * 注文数.
     */
    private Integer orderCount;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 金额.
     */
    private BigDecimal price;

    /**
     * 注文区分.
     */
    private String orderType;
    
    /**
     * 予約数.
     */
    private Integer reservateCount;
}
