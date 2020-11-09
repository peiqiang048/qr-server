package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文情報取得条件.
 */
@Data
public class GetShareOrderInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;
}
