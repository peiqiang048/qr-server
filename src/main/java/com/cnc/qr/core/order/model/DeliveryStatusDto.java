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
public class DeliveryStatusDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状態コード.
     */
    private Integer statusCd;

    /**
     * 状態名.
     */
    private String statusName;

}
