package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 注文詳細情報取得.
 */
@Data
public class GetOrderDetailInfoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

}
