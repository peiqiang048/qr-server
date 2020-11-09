package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得条件.
 */
@Data
public class GetItemCategoryInputDto implements Serializable {

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
     * 店舗媒体用途区分.
     */
    private String terminalDistinction;

    /**
     * 受付ID.
     */
    private String receivablesId;
    
    /**
     * 商品取得区分.
     */
    private String itemGetType;
}
