package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableReceivablesDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * グループID.
     */
    private Integer groupId;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * 受付ID.
     */
    private String receivablesId;
    
    /**
     * 目指せ受付ID.
     */
    private String realReceivablesId;
}
