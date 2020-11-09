package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支払方式.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 支払コード .
     */
    private String paymentCode;

    /**
     * 支払名.
     */
    private String paymentName;
}
