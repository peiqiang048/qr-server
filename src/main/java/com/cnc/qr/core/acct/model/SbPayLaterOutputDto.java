package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 後払処理結果.
 */
@Data
public class SbPayLaterOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 支払結果.
     */
    private String respCode;

    /**
     * 取消結果.
     */
    private String cancelRespCode;

    /**
     * メッセージ.
     */
    private String message;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;
}