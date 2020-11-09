package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 呼出中情報取得条件.
 */
@Data
public class GetCallInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付番号.
     */
    private Integer receivablesNo;
}
