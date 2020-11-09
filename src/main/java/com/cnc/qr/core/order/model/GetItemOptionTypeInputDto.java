package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品オプション種類情報取得検索条件.
 */
@Data
public class GetItemOptionTypeInputDto implements Serializable {

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
     * 商品ID.
     */
    private Integer itemId;
}
