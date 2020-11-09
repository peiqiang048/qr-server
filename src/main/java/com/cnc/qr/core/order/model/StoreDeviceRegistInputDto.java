package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 売上リスト.
 */
@Data
public class StoreDeviceRegistInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * トークン.
     */
    private String token;

    /**
     * 型番.
     */
    private String model;


}
