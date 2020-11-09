package com.cnc.qr.core.order.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 予約情報取得条件.
 */
@Data
public class GetReservateInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 予約ID.
     */
    private Integer reservateId;

    /**
     * 语言.
     */
    private String languages;
}
