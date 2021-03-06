package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * コース情報取得条件.
 */
@Data
public class GetCourseInputDto implements Serializable {

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
     * コースID.
     */
    private Integer courseId;
}
