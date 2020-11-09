package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * プリンター保存インプット.
 */
@Data
public class RegistPrintInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * プリンターID.
     */
    private Integer printId;

    /**
     * プリンター名.
     */
    private String printName;

    /**
     * プリンタブランドCD.
     */
    private String brandCode;

    /**
     * 接続方法CD.
     */
    private String connectionMethodCode;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * ブルートゥース名.
     */
    private String blueToothName;

    /**
     * モデル.
     */
    private String printModel;

    /**
     * チケット幅CD.
     */
    private String printSizeCode;
}
