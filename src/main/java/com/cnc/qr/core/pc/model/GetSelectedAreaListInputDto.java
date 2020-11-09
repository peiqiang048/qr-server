package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 指定区域取得条件.
 */
@Data
public class GetSelectedAreaListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * エリア区分.
     */
    private String areaType;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 言語.
     */
    private String languages;
}
