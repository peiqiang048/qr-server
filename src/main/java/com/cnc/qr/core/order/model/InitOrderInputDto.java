package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class InitOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 桌号.
     */
    private Integer tableId;

    /**
     * 放題ID.
     */
    private List<BuffetInfoDto> buffetId;

    /**
     * コースID.
     */
    private List<CourseInfoDto> courseId;
}
