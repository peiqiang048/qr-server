package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 飲食区分取得検索条件.
 */
@Data
public class TrioPayLaterInputDto implements Serializable {

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
     * 支付价格.
     */
    private BigDecimal payAmount;
    
    /**
     * 二维码CD.
     */
    private String payCodeId;
    
    /**
     * 就餐区分.
     */
    private String takeOutFlag;
    
    /**
     * 值引额.
     */
    private BigDecimal priceDiscountAmount;
    
    /**
     * 值引率.
     */
    private BigDecimal priceDiscountRate;
}
