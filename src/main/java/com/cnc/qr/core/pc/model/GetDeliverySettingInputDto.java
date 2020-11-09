package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 出前設定取得条件.
 */
@Data
public class GetDeliverySettingInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;

    /**
     * 都道府县.
     */
    private String prefectureId;

    /**
     * 市区町村.
     */
    private String cityId;
}
