package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * ディフォルト利用時間取得.
 */
@Data
public class GetDefaultUseTimeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * ディフォルト利用時間.
     */
    private BigDecimal defaultUseTime;
    
}
