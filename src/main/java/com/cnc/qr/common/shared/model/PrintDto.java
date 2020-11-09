package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * キッチン印刷入力情報.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * bluetooth名.
     */
    private String bluetoothName;

    /**
     * ブランド.
     */
    private String brandCode;

    /**
     * 接続方式.
     */
    private String connectionMethodCode;

    /**
     * 伝票幅CD.
     */
    private String printSize;
}
