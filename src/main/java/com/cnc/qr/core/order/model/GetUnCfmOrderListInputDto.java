package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文一覧情報取得条件.
 */
@Data
public class GetUnCfmOrderListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 确认状态.
     */
    private String sureFlag;

    /**
     * 飲食区分.
     */
    private String takeoutFlag;

    /**
     * テーブルID.
     */
    private Integer tableId;

}
