package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗受付情報取得結果.
 */
@Data
public class GetReceivablesInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付番号.
     */
    private String receivablesNo;

    /**
     * プリンター情報.
     */
    private String printInfo;
}
