package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 市区町村情報.
 */
@Data
public class CityListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 市区町村名称.
     */
    private String cityName;
}
