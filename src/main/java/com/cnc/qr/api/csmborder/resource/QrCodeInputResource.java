package com.cnc.qr.api.csmborder.resource;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QRコード印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeInputResource {
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * テーブルID.
     */
    private Integer tableId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * ユーザ名.
     */
    private String userName;
}
