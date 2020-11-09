package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返品詳細伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnItemDto implements Serializable {

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

}
