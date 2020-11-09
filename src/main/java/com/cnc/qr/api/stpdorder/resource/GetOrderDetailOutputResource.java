package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemNumDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class GetOrderDetailOutputResource extends CommonOutputResource {

    /**
     * 受付時間.
     */
    private String receptionTime;

    /**
     * 受付番号.
     */
    private String receptionNo;


    /**
     * テーブル名.
     */
    private String tableName;


    /**
     * 小計.
     */
    private String subtotal;


    /**
     * 外税.
     */
    private String foreignTax;


    /**
     * 合計.
     */
    private String total;


    /**
     * 割引率.
     */
    private String discountTax;


    /**
     * 値引額.
     */
    private String priceDiscountAmount;


    /**
     * 支払金額.
     */
    private String paymentAmount;


    /**
     * 商品リスト.
     */
    private List<OrderItemNumDto> itemList;
}
