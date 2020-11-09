package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 呼出中情報取得検索結果.
 */
@Data
public class GetCallDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 桌名.
     */
    private String tableName;

    /**
     * 受付番号.
     */
    private String receivablesNo;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 呼叫时间.
     */
    private String callDateTime;

    /**
     * 呼叫次数.
     */
    private String callCount;
}
