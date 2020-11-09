package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前区分リストDTO.
 */
@Data
public class DeliveryFlagListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 出前区分コード.
     */
    private String deliveryFlagCd;

    /**
     * 出前区分名.
     */
    private String deliveryFlagName;
}
