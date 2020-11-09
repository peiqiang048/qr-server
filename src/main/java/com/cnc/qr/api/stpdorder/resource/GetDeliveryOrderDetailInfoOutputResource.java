package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.ItemsDetailDto;
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
public class GetDeliveryOrderDetailInfoOutputResource extends CommonOutputResource {

    /**
     * 受付時間.
     */
    private String receptionTime;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 小計.
     */
    private String orderAmount;

    /**
     * 外税.
     */
    private String foreignTax;

    /**
     * 合計.
     */
    private String totalAmount;

    /**
     * 支払金額.
     */
    private String paymentAmount;

    /**
     * 支払方式.
     */
    private String paymentMethod;

    /**
     * 氏名.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String telNumber;

    /**
     * 出前時間.
     */
    private String deliveryTime;

    /**
     * メール.
     */
    private String mailAddress;

    /**
     * アドレス.
     */
    private String address;

    /**
     * 状態.
     */
    private String status;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

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
    private List<ItemsDetailDto> itemList;
}
