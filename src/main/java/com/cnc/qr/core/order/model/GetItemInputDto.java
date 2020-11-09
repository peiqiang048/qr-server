package com.cnc.qr.core.order.model;

import java.io.Serializable;

import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class GetItemInputDto implements Serializable {

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
     * 放題ID.
     */
    private Integer buffetId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 店舗媒体用途区分.
     */
    private String terminalDistinction;

    /**
     * コースフラグ.
     */
    private String courseFlag;

    /**
     * 放題ID.
     */
    private List<Integer> buffetIdList;
}
