package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文確定情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 小計.
     */
    private BigDecimal orderAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 配達費.
     */
    private BigDecimal cateringCharge;

    /**
     * 合計.
     */
    private BigDecimal totalAmount;

    /**
     * 商品情報リスト.
     */
    private List<OrderItemsDto> itemList;

    /**
     * 出前区分.
     */
    private String deliveryTypeFlag;

    /**
     * 状態.
     */
    private String status;

    /**
     * 氏名.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String phoneNumber;

    /**
     * 出前開始時間.
     */
    private ZonedDateTime deliveryStartTime;

    /**
     * 出前終了時間.
     */
    private ZonedDateTime deliveryEndTime;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 町域番地ID.
     */
    private String blockId;

    /**
     * 住所.
     */
    private String address;

    /**
     * メール.
     */
    private String email;

    /**
     * 要望.
     */
    private String comment;

}
