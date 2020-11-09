package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * QR発行アウトプット.
 */
@Data
public class QrCodeIssueOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付ID.
     */
    private String receptionId;
}
