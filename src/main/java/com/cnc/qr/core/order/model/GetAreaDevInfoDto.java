package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * エリア一覧情報取得検索結果.
 */
@Data
public class GetAreaDevInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * エリアID.
     */
    private String areaId;

    /**
     * エリア名.
     */
    private String areaName;
}
