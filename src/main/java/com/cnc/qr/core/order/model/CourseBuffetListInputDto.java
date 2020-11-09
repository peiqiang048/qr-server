package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * コース＆放题.
 */
@Data
public class CourseBuffetListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 语言.
     */
    private String languages;
}
