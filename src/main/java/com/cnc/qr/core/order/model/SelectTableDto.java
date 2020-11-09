package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 席選択リスト.
 */
@Data
public class SelectTableDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 席ID.
     */
    private Integer tableId;

}
