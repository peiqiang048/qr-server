package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * プリンター情報取得検索結果.
 */
@Data
public class PrintDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * プリンターID.
     */
    private String printId;

    /**
     * プリンター名.
     */
    private String printName;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * ブルートゥース.
     */
    private String blueToothName;

    /**
     * ブランド名.
     */
    private String brandName;

    /**
     * モデル.
     */
    private String printModel;

    /**
     * 接続方法名.
     */
    private String connectionMethodName;


    /**
     * プリンターサイズ.
     */
    private String printSize;

}
