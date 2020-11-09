package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetItemListInputDto implements Serializable {

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
     * 商品名.
     */
    private String itemName;
    
    /**
     * 状態.
     */
    private String itemStatus;
    
    /**
     * キチン.
     */
    private Integer kitchenId;
    
    /**
     * 店舗媒体用途区分.
     */
    private String terminalDistinction;
}
