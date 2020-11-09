package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * トップ画面情報取得条件.
 */
@Data
public class GetHomePageInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
}
