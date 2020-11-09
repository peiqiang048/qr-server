package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 放题情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuffetInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 放题ID.
     */
    private Integer buffetId;

    /**
     * 放题单价.
     */
    private BigDecimal buffetAmount;

    /**
     * 放题数.
     */
    private Integer buffetCount;

}
