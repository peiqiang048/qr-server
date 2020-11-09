package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * プリンター編集取得検索結果.
 */
@Data
public class GetPrintOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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

    /**
     * ブランドリスト.
     */
    private List<PrintBrandDto> brandList;

    /**
     * 接続方法リスト.
     */
    private List<PrintConnectionMethodDto> connectionMethodList;

    /**
     * チケット幅リスト.
     */
    private List<PrintSizeDto> printSizeList;
}
