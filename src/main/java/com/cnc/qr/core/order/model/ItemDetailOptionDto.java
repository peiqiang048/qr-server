package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品明細オプション情報.
 */
@Data
public class ItemDetailOptionDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプションCode.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;

    /**
     * 商品スプレッド.
     */
    private String diffPrice;

    /**
     * 選択状態.
     */
    private String selectionStatus;

    /**
     * 数量.
     */
    private Integer optionNumber;
}
