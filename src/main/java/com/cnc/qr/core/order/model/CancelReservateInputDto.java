package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class CancelReservateInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 予約IDリスト.
     */
    private List<ReservateIdDto> reservateIdList;


}
