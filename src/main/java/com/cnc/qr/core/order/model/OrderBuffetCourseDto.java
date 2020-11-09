package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBuffetCourseDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID.
     */
    @NotNull
    private Integer itemId;

    /**
     * 商品单价.
     */
    private BigDecimal itemPrice;
    
    /**
     *商品個数.
     */
    private Integer itemCount;

}
