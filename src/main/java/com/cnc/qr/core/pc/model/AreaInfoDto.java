package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エリア情報.
 */
@Data
@NoArgsConstructor
public class AreaInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * エリアID.
     */
    private String areaId;

    /**
     * エリア名.
     */
    private String areaName;

    /**
     * コンストラクタ.
     *
     * @param areaId   エリアID
     * @param areaName エリア名
     */
    public AreaInfoDto(Integer areaId, String areaName) {
        this.areaId = areaId.toString();
        this.areaName = areaName;
    }
}
