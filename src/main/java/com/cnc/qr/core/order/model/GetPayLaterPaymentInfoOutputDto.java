package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 支払方式取得検索結果.
 */
@Data
public class GetPayLaterPaymentInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 支払方式リスト.
     */
    private List<PaymentMethodDto> paymentMethodList;
}
