package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗画像パス.
 */
@Data
public class StorePicUrlDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表示顺.
     */
    private Integer sortNo;

    /**
     * 画像パス.
     */
    private String url;
}
