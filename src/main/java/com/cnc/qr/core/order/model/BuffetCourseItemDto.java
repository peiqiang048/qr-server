package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuffetCourseItemDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 税区分.
     */
    private String taxCode;
    
    /**
     * 商品説明.
     */
    private String itemInfo;
    
    /**
     * 商品区分.
     */
    private String itemType;
}
