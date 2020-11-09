package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * SBペイメント返金情報.
 */
@Data
public class SbPaymentRefundsInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文番号.
     */
    private String orderNo;
}
