package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コース情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTypeFlagDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 出前仕方コード.
     */
    private Integer deliveryTypeFlagCd;

    /**
     * 出前仕方名.
     */
    private String deliveryTypeFlagName;

}
