package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 売上リスト.
 */
@Data
public class AmountSoldDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 支払時間.
     */
    private String paymentTime;


    /**
     * 支払金額.
     */
    private String paymentAmount;

    /**
     * 支払方式.
     */
    private String paymentMethod;


}
