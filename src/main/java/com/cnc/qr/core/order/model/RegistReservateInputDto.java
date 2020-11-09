package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 予約.
 */
@Data
public class RegistReservateInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 予約ID.
     */
    private Integer reservateId;

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


}
