package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 客の商品情報.
 */
@Data
public class NoItemsInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 客番号.
     */
    private String no;

    /**
     * 商品情報.
     */
    private List<ItemInfoDto> items;
}
