package com.cnc.qr.core.order.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 注文一覧情報取得条件.
 */
@Data
public class GetOrderHistoryListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

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
