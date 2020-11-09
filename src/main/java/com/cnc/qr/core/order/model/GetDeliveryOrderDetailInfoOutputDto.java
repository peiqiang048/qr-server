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
public class GetDeliveryOrderDetailInfoOutputDto implements Serializable {

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
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 小計.
     */
    private BigDecimal orderAmount;

    /**
     * 外税.
     */
    private BigDecimal foreignTax;

    /**
     * 合計.
     */
    private BigDecimal totalAmount;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

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
     * メール.
     */
    private String mailAddress;

    /**
     * 出前時間.
     */
    private String deliveryTime;

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
     * 出前仕方.
     */
    private String deliveryType;

    /**
     * 配達費.
     */
    private BigDecimal cateringCharge;

    /**
     * 要望.
     */
    private String comment;

    /**
     * 商品リスト.
     */
    private List<ItemsDetailDto> itemList;
}
