package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 * 予約情報.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryOrderInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 支払方式コード.
     */
    private String paymentCode;

    /**
     * 注文時間.
     */
    private String orderTime;

    /**
     * 受付番号.
     */
    private String receivablesNo;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 支払金額.
     */
    private String paymentAmount;

    /**
     * 状態コード.
     */
    private String statusCd;

    /**
     * 状態名.
     */
    private String statusName;
}
