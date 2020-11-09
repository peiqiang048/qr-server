package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 後払データ.
 */
@Data
public class RegistDutchAccountInputDto implements Serializable {

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
     * 新受付ID.
     */
    private String newReceivablesId;
    
    /**
     * 新注文サマリID.
     */
    private String newOrderSummaryId;
    
    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * 值引额.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 值引率.
     */
    private BigDecimal priceDiscountRate;
    
    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;
    
    /**
     * 外税.
     */
    private BigDecimal sotoTax;
    
    /**
     * 小計.
     */
    private BigDecimal subtotal;
    
    /**
     * 支払い方式コード.
     */
    private String paymentMethodCode;
    
    /**
     * お預かり金額.
     */
    private BigDecimal custody;
    
    /**
     * 人数.
     */
    private Integer customerCount;
    
    /**
     * 最後注文フラグ.
     */
    private String lastOrderFlag;
    
    /**
     * 商品リスト.
     */
    private List<ItemListDto> itemList;
}
