package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 * 予約情報.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservateInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 予約ID.
     */
    private String reservateId;

    /**
     * 来店日時.
     */
    private String reservateTime;

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
     * コース・放題.
     */
    private String courseBuffet;

    /**
     * 要望.
     */
    private String comment;

    /**
     * 状態.
     */
    private String status;

    /**
     * テーブル名.
     */
    private String tableName;
}
