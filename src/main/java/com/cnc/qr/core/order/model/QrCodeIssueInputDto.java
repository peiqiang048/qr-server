package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * QR発行インプット.
 */
@Data
public class QrCodeIssueInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * テーブルID.
     */
    private Integer tableId;

}
