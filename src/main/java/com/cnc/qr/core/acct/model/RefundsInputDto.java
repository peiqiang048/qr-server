package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 飲食区分取得検索条件.
 */
@Data
public class RefundsInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文番号.
     */
    private String orderNo;
}
