package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 出前印刷インプット.
 */
@Data
public class PrintDeliverOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 店舗ID.
     */
    private String storeId;


    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * レシート種類フラグ（１：キッチン、２：レシート、３：領収書）.
     */
    private String deliverPrintType;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;
}
