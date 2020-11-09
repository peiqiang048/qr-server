package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 支払方式取得条件.
 */
@Data
public class GetPayLaterPaymentInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

}
