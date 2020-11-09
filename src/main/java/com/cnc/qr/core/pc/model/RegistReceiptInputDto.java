package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * レシート保存インプット.
 */
@Data
public class RegistReceiptInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 票据ID.
     */
    private String receiptId;

    /**
     * プリンターID.
     */
    private String printId;

    /**
     * 票据名.
     */
    private String receiptName;
}
