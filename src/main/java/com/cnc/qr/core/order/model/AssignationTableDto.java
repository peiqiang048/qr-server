package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 配せき情報取得検索結果.
 */
@Data
public class AssignationTableDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 受付時間.
     */
    private String receptionTime;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * 滞在時間.
     */
    private String stayTime;

    /**
     * 受付ID.
     */
    private String receivablesId;

}
