package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 飲食区分取得検索条件.
 */
@Data
public class RegistPaymentInputDto implements Serializable {

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
     * 值引额.
     */
    private BigDecimal priceDiscountAmount;
    
    /**
     * 值引率.
     */
    private BigDecimal priceDiscountRate;
    
    /**
     * 支払い金額.
     */
    private BigDecimal paymentAmount;
    
    /**
     * 就餐区分.
     */
    private String takeoutFlag;
    
    /**
     * 支払い方式コード.
     */
    private String paymentMethodCode;
}
