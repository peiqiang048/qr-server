package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 会計PAD注文詳細情報取得検索結果.
 */
@Data
public class GetOrderDetailInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;


    /**
     * 语言.
     */
    private String languages;
}
