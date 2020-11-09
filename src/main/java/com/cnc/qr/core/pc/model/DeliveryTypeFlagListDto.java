package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前仕方リストDTO.
 */
@Data
public class DeliveryTypeFlagListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     *出前仕方コード.
     */
    private String deliveryTypeFlagCd;

    /**
     * 出前仕方名.
     */
    private String deliveryTypeFlagName;
}
