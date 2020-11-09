package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class CourseBuffetConfirmOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 小計.
     */
    private BigDecimal subtotalPrice;
    
    /**
     * 外税.
     */
    private BigDecimal foreignTax;
    
    /**
     * 合計.
     */
    private BigDecimal totalPrice;
}
