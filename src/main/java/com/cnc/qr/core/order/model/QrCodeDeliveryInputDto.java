package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出前QRコード印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeDeliveryInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザ名.
     */
    private String userName;
}
