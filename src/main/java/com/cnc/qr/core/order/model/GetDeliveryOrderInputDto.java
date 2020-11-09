package com.cnc.qr.core.order.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 注文編集取得条件.
 */
@Data
public class GetDeliveryOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 出前区分.
     */
    private String deliveryTypeFlag;

    /**
     * 言語.
     */
    private String languages;
}
