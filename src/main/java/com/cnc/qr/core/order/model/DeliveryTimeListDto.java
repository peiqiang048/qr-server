package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前時間情報.
 */
@Data
public class DeliveryTimeListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 出前時間.
     */
    private String deliveryTime;
}
