package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得条件.
 */
@Data
public class GetDeliveryCategoryInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;
    
    /**
     * レベル.
     */
    private Integer gradation;

    /**
     * 出前区分.
     */
    private String deliveryTypeFlag;
}
