package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;

/**
 * 注文情報.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付番号.
     */
    private String receivablesNo;

    /**
     * 注文金額.
     */
    private BigDecimal orderPrice;

    /**
     * 注文ステータス.
     */
    private String orderStatus;

    /**
     * 注文タイプ.
     */
    private String orderType;


    /**
     * 注文最新時間.
     */
    private ZonedDateTime updDateTime;
}
