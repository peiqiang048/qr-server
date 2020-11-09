package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前注文編集.
 */
@Data
public class UpdateDeliveryOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

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
    private String other;

}
