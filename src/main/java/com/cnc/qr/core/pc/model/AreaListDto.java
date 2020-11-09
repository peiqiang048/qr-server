package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * エリアリストDTO.
 */
@Data
public class AreaListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;
    
    /**
     * 町域番地ID.
     */
    private String blockId;
    
    /**
     * エリアテキスト.
     */
    private String areaText;
}
