package com.cnc.qr.api.csmborder.resource;

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
     * 注文ID.
     */
    private String orderId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 小計金额.
     */
    private BigDecimal orderAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 支払済金額.
     */
    private BigDecimal paidPrice;

    /**
     * 未支払金額.
     */
    private BigDecimal unpaidPrice;

    /**
     * 支払済商品リスト.
     */
    private List<OrderItemInfoDto> paidItemList;

    /**
     * 未支払商品リスト.
     */
    private List<OrderItemInfoDto> unpaidItemList;
}
