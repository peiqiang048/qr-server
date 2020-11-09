package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 单位情報取得検索結果.
 */
@Data
public class UnitDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 单位ID.
     */
    private String unitId;

    /**
     * 单位名.
     */
    private String unitName;

}
