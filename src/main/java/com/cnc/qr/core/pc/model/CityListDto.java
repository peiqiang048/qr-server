package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 市区町村リストDTO.
 */
@Data
public class CityListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 市区町村名称.
     */
    private String cityName;
}
