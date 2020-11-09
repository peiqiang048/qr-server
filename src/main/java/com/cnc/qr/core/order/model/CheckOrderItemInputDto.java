package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class CheckOrderItemInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付番号.
     */
    private String receivablesId;

    /**
     * チェック区分.
     */
    private String itemBuffetType;
}
