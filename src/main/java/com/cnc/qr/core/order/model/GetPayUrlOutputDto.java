package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class GetPayUrlOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 支払いURL情報.
     */
    private String payUrl;
}
