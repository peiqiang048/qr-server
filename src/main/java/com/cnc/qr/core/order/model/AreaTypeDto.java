package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * エリア情報取得検索結果.
 */
@Data
public class AreaTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * エリアID.
     */
    private Integer areaId;

    /**
     * エリア短縮名.
     */
    private String areaName;
}
