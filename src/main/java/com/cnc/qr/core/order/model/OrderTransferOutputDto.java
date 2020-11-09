package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class OrderTransferOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 遷移先区分.
     */
    private String transitionType;
}
