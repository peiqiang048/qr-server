package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemDetailInfoDto;
import com.cnc.qr.core.order.model.PaymentMethodDto;
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
public class GetAccountInitOutputResource extends CommonOutputResource {

    /**
     * 支払方式リスト.
     */
    private List<PaymentMethodDto> paymentTypeList;

    /**
     * 商品リスト.
     */
    private List<OrderItemDetailInfoDto> itemList;

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

}
