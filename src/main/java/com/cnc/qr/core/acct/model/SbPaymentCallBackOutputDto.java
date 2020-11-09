package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * SBペイメントコールバック処理結果.
 */
@Data
public class SbPaymentCallBackOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 処理結果ステータス.
     */
    private String resResult;

    /**
     * メッセージ.
     */
    private String message;
}
