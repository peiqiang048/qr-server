package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 売上リスト.
 */
@Data
public class CourseBuffetDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コース＆放题ID.
     */
    private Integer courseBuffetId;

    /**
     * コース＆放题名称.
     */
    private String courseBuffetName;

    /**
     * 価格.
     */
    private String courseBuffetPrice;

    /**
     * 商品区分.
     */
    private String itemType;

    /**
     * 画像パス.
     */
    private String imagePath;

    /**
     * 画像パス.
     */
    private String shortImagePath;

    /**
     * 画像パス.
     */
    private String videoPath;

}
