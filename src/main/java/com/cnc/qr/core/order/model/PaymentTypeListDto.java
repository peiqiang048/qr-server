package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 支付方式情報.
 */
@Data
public class PaymentTypeListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 支付方式ID.
     */
    private String paymentTypeId;

    /**
     * 支付方式名称.
     */
    private String paymentTypeName;
    
    /**
     * 支付方式図.
     */
    private String paymentTypePic;
}
