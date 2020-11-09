package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 席解除一覧取得条件.
 */
@Data
public class GetSeatReleaseListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
}
