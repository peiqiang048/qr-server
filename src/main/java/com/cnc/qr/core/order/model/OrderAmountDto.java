package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * オーダー小計Dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAmountDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文額.
     */
    private BigDecimal itemPrice;

    /**
     * 返品元注文明細ID.
     */
    private Integer returnOrderDetailId;

}
