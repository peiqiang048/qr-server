package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * テーブルアウトプット情報.
 */
@Data
public class TableOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * エリア名.
     */
    private String areaTypeName;

    /**
     * テーブル上限客数.
     */
    private Integer tableSeatCount;

    /**
     * 客数.
     */
    private Integer customerCount;

    /**
     * オーダー数.
     */
    private Integer orderCount;

    /**
     * オーダーリスト.
     */
    private List<OrderDto> orderList;
}
