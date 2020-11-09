package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * コース＆放题.
 */
@Data
public class CourseBuffetListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;


    /**
     * コース＆放题リスト.
     */
    private List<CourseBuffetDto> courseBuffetList;
}
