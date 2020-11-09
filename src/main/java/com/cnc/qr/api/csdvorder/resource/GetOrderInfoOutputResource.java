package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetOrderInfoOutputResource extends CommonOutputResource {

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
     * 商品リスト.
     */
    private List<OrderItemInfoDto> itemList;
    
    /**
     * 配達費.
     */
    private BigDecimal cateringCharge;
}
