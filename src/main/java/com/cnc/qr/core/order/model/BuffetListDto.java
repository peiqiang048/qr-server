package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuffetListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 放題ID.
     */
    private Integer buffetId;

    /**
     * 放題名.
     */
    private String buffetName;

    /**
     * 放題金额.
     */
    private BigDecimal buffetAmount;
}
