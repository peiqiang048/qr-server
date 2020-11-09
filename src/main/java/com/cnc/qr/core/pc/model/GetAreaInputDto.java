package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * エリア情報取得条件.
 */
@Data
public class GetAreaInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * エリアID.
     */
    private Integer areaId;
}
