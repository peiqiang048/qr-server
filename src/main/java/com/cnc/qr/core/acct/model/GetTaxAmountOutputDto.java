package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 後払処理結果.
 */
@Data
public class GetTaxAmountOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 小計.
     */
    private String orderAmount;

    /**
     * 外税.
     */
    private String foreignTax;

    /**
     * 合計.
     */
    private String total;
}
