package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 店舗媒体情報取得検索結果.
 */
@Data
public class GetStoreAdverPicOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 画像パスリスト.
     */
    private List<StorePicUrlDto> picUrls;
}
