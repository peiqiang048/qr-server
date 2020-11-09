package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableOrderDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付NO.
     */
    private Integer receptionNo;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

    /**
     * 更新日時.
     */
    private ZonedDateTime updDateTime;

    /**
     * 注文区分.
     */
    private String orderType;

    /**
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 商品状態.
     */
    private String itemStatus;


}
