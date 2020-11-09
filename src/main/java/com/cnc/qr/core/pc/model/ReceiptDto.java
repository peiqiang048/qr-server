package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * レシート情報取得検索結果.
 */
@Data
public class ReceiptDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 票据ID.
     */
    private String receiptId;

    /**
     * 票据名.
     */
    private String receiptName;

}
