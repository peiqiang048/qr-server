package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品詳細情報取得条件.
 */
@Data
public class GetItemDetailInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 放題フラグ.
     */
    private String buffetItemFlag;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 注文明細ID.
     */
    private Integer orderDetailId;

    /**
     * 言語.
     */
    private String languages;
}
