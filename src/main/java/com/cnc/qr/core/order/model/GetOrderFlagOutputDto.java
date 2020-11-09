package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文フラグ取得結果.
 */
@Data
public class GetOrderFlagOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文フラグ.
     */
    private Integer orderFlag;
}
