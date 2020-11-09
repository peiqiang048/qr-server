package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class GetItemBySpeechOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品情報.
     */
    private List<GetItemBySpeechDto> items;

    /**
     * 認識結果.
     */
    private String transcript;
}
