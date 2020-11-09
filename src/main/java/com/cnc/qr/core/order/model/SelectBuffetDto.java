package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 放題選択リスト.
 */
@Data
public class SelectBuffetDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 放題ID.
     */
    private Integer buffetId;

    /**
     * 放題個数.
     */
    private Integer buffetCount;

}
