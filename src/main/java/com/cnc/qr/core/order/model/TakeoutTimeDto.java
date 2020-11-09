package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 持帰り時間情報.
 */
@Data
public class TakeoutTimeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 持帰り時間.
     */
    private String takeoutTime;
}
