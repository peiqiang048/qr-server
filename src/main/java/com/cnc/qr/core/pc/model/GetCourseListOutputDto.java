package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コース情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コースリスト.
     */
    private List<GetCourseList> courseList;

    /**
     * 数据条数.
     */
    private Long totalCount;
}
