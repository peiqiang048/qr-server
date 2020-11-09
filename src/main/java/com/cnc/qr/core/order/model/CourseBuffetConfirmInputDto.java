package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class CourseBuffetConfirmInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 商品リスト.
     */
    private List<ItemInfoDto> itemList;

}
