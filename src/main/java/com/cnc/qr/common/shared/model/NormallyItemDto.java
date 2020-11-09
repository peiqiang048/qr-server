package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormallyItemDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 商品名称.
     */
    private String itemName;
    /**
     * 商品個数.
     */
    private String itemCount;

    /**
     * 商品価格.
     */
    private String itemPrice;


    /**
     * 軽減税率適用区分.
     */
    private String taxReliefApplyType;

    /**
     * 商品オプション詳細リスト.
     */
    private List<ItemOptionDto> itemOptionList;

    /**
     * 商品コース詳細.
     */
    private List<String> itemCourseList;

}
