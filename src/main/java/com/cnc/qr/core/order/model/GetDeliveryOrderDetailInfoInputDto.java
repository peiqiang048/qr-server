package com.cnc.qr.core.order.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 出前注文明細情報取得条件.
 */
@Data
public class GetDeliveryOrderDetailInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 言語.
     */
    private String languages;
}
