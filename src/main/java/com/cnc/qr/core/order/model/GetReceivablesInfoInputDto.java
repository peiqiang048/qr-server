package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 店舗受付情報取得条件.
 */
@Data
public class GetReceivablesInfoInputDto implements Serializable {

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
     * テーブルID.
     */
    private List<TableIdDto> tableId;

    /**
     * 放題ID.
     */
    private List<BuffetInfoDto> buffetId;

    /**
     * コースID.
     */
    private List<CourseInfoDto> courseId;

}
