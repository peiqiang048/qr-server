package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品詳細情報.
 */
@Data
@AllArgsConstructor
public class ItemDetailDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * 商品備考.
     */
    private String itemInfo;

    /**
     * 商品オプション類型Code.
     */
    private String optionTypeCd;

    /**
     * 商品オプションCode.
     */
    private String optionCode;

    /**
     * 数量.
     */
    private Integer optionNumber;

    /**
     * 商品オプションフラグ.
     */
    private String optionFlag;
}
