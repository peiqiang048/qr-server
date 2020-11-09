package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

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
     * 割引率.
     */
    private BigDecimal discountTax;


    /**
     * 値引額.
     */
    private BigDecimal priceDiscountAmount;


    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;


    /**
     * 商品リスト.
     */
    private List<OrderItemNumDto> itemList;
}
