package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 单位保存インプット.
 */
@Data
public class RegistUnitInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 单位ID.
     */
    private Integer unitId;

    /**
     * 单位名.
     */
    private String unitName;
}
