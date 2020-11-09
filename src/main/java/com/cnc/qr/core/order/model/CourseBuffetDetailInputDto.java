package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class CourseBuffetDetailInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId; 

    /**
     * コース＆放题ID.
     */
    private Integer courseBuffetId;
    
    /**
     * 言語.
     */
    private String languages;

}
