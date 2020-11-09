package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 席種類情報取得検索結果.
 */
@Data
public class GetTableTypeInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 席種類ID.
     */
    private String tableTypeId;

    /**
     * 席種類名称.
     */
    private String tableTypeName;

    /**
     * 色.
     */
    private String tableColor;
}
