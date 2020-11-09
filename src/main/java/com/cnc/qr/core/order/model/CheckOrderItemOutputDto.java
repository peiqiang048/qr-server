package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得検索結果.
 */
@Data
public class CheckOrderItemOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * チェック結果.
     */
    private Integer existFlag;
    
    /**
     * 未確認コース放題数量.
     */
    private Integer unconfirmedCount;
}
