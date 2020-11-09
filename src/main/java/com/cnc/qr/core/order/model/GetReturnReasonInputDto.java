package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 返品原因情報取得条件.
 */
@Data
public class GetReturnReasonInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;
}
