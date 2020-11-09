package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 返品原因情報取得検索結果.
 */
@Data
public class GetReturnReasonDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 返品原因コード.
     */
    private String returnReasonCode;

    /**
     * 返品原因名称.
     */
    private String returnReasonName;
}
