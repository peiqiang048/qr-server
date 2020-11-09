package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品オップシュ情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemOptionDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * option种类Code.
     */
    private String optionTypeCd;

    /**
     * optionCode.
     */
    private String optionCode;

    /**
     * option数量.
     */
    private Integer optionCount;

    /**
     * option差额.
     */
    private BigDecimal optionDiffPrice;
}
