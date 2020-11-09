package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 配達時間情報.
 */
@Data
public class CateringTimeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配達時間.
     */
    private String cateringTime;
}
