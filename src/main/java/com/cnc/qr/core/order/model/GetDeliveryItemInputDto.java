package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetDeliveryItemInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;

    /**
     * 商品カテゴリー.
     */
    private Integer categoryId;

    /**
     * 検索内容.
     */
    private String searchInfo;

    /**
     * 出前区分.
     */
    private String deliveryTypeFlag;
}
