package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗画像パス.
 */
@Data
public class PicUrlDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 画像パス.
     */
    private String picUrl;
}
