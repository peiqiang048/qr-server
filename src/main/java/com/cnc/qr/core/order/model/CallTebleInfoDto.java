package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 呼出情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallTebleInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 受付番号.
     */
    private Integer receptionNo;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * テーブル名.
     */
    private String tableName;


}
