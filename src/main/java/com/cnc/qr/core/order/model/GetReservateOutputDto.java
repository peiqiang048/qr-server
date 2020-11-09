package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 返品原因情報取得検索結果.
 */
@Data
public class GetReservateOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 来店日時.
     */
    private String reservateTime;

    /**
     * 利用時間.
     */
    private String useTime;

    /**
     * 顧客名前.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String telNumber;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 予約区分.
     */
    private String reservateType;

    /**
     * コース選択リスト.
     */
    private List<SelectCourseDto> selectCourseList;

    /**
     * 放題選択リスト.
     */
    private List<SelectBuffetDto> selectBuffetList;

    /**
     * 席選択リスト.
     */
    private List<SelectTableDto> selectTableList;

    /**
     * 要望.
     */
    private String comment;

    /**
     * エリアリスト.
     */
    private List<GetAreaInfoDto> areaList;

    /**
     * 席リスト.
     */
    private List<GetTableInfoDto> tableList;

    /**
     * コースリスト.
     */
    private List<CourseDto> courseList;

    /**
     * 放題リスト.
     */
    private List<BuffetDto> buffetList;

    /**
     * 席種類リスト.
     */
    private List<GetTableTypeInfoDto> tableTypeList;
}
