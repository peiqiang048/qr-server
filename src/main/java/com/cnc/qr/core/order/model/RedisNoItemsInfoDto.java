package com.cnc.qr.core.order.model;

import java.util.List;
import lombok.Data;

/**
 * 客の商品情報.
 */
@Data
public class RedisNoItemsInfoDto {

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 操作ID.
     */
    private String opsTypeCode;

    /**
     * 客番号.
     */
    private String no;

    /**
     * 商品情報.
     */
    private List<ItemInfoDto> items;
}
