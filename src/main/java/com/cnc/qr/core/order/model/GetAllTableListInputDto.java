package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 席一覧情報取得条件.
 */
@Data
public class GetAllTableListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 来店日時.
     */
    private String reservateTime;

    /**
     * 利用時間.
     */
    private BigDecimal useTime;
}
