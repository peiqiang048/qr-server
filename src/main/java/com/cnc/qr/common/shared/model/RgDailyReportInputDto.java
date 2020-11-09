package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品オプション類型情報取得検索条件.
 */
@Data
public class RgDailyReportInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
}
