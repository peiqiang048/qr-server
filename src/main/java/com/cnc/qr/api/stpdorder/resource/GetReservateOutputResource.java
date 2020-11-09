package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.BuffetDto;
import com.cnc.qr.core.order.model.CourseDto;
import com.cnc.qr.core.order.model.GetAreaInfoDto;
import com.cnc.qr.core.order.model.GetTableInfoDto;
import com.cnc.qr.core.order.model.GetTableTypeInfoDto;
import com.cnc.qr.core.order.model.SelectBuffetDto;
import com.cnc.qr.core.order.model.SelectCourseDto;
import com.cnc.qr.core.order.model.SelectTableDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetReservateOutputResource extends CommonOutputResource {

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
