package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemDetailInfoDto;
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
public class GetOrderItemListOutputResource extends CommonOutputResource {

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 商品リスト.
     */
    private List<OrderItemDetailInfoDto> itemList;

    /**
     * 支払方式.
     */
    private String accountsType;
    
    /**
     * 支払区分.
     */
    private String paymentDistinction;
}
