package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemNumDto;
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
public class GetOrderOutputResource extends CommonOutputResource {

    /**
     * 人数.
     */
    private String customerCount;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 注文金额.
     */
    private String orderAmount;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 小計.
     */
    private BigDecimal subtotal;

    /**
     * 外税.
     */
    private BigDecimal foreignTax;

    /**
     * 合計.
     */
    private BigDecimal total;

    /**
     * 割引额.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 割引率.
     */
    private BigDecimal priceDiscountRate;

    /**
     * 未收.
     */
    private BigDecimal unpay;

    /**
     * 受付No.
     */
    private String receivablesNo;

    /**
     * テーブルID.
     */
    private String tableId;

}
