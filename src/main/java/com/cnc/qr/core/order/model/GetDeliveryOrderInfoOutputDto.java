package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文済情報取得検索結果.
 */
@Data
public class GetDeliveryOrderInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 氏名.
     */
    private String customerName;
    
    /**
     * 電話番号.
     */
    private String phoneNumber;
    
    /**
     * 出前時間.
     */
    private String deliveryTime;
    
    /**
     * メール.
     */
    private String email;
    
    /**
     * 状態.
     */
    private String status;
    
    /**
     * 支払方式.
     */
    private String paymentType;
    
    /**
     * 住所.
     */
    private String address;

    /**
     * 小計金额.
     */
    private BigDecimal orderAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 合計.
     */
    private BigDecimal totalAmount;
    
    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;
    
    /**
     * 出前仕方.
     */
    private String deliveryType;

    /**
     * 要望.
     */
    private String comment;
    
    /**
     * 配達費.
     */
    private BigDecimal cateringCharge;

    /**
     * 商品リスト.
     */
    private List<OrderItemInfoDto> itemList;
}
