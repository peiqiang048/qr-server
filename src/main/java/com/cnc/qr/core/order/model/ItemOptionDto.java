package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品オプション情報取得検索結果.
 */
@Data
public class ItemOptionDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品オプションID.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;

    /**
     * 商品オプション順.
     */
    private String sortOrder;

    /**
     * 商品スプレッド.
     */
    private String diffPrice;

    /**
     * 商品数量.
     */
    private Integer itemCount;
}
