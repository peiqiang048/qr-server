package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出前QRコード印刷出力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeDeliveryOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンター情報.
     */
    private String printInfo;

}
