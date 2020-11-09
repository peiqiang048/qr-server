package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 注文情報.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnCfmOrderInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文時間.
     */
    private String orderTime;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 受付番号.
     */
    private String receivablesNo;

    /**
     * 注文金額.
     */
    private BigDecimal orderAmount;

    /**
     * 受付ID.
     */
    private String receivablesId;

}
