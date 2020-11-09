package com.cnc.qr.core.order.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 出前注文明細情報取得条件.
 */
@Data
public class GetDeliveryOrderEditInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 言語.
     */
    private String languages;
}
