package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 支払方式取得検索結果.
 */
@Data
public class GetPaymentInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 前後支払区分.
     */
    private String paymentType;

    /**
     * 支払方式リスト.
     */
    private List<PaymentMethodDto> paymentMethodList;
}
