package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 人数変更データ.
 */
@Data
public class ChangeCustomerCountInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 人数.
     */
    private Integer customerPeopleCount;
}
