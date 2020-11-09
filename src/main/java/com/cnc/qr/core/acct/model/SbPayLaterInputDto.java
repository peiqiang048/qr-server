package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 後払データ.
 */
@Data
public class SbPayLaterInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 支払金額.
     */
    private BigDecimal payAmount;

    /**
     * QRコード.
     */
    private String payCodeId;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 值引额.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 值引率.
     */
    private BigDecimal priceDiscountRate;
}
