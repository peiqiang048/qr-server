package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * SBペイメントコールバック情报.
 */
@Data
public class SbPaymentCallBackInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 購入ID.
     */
    private String orderId;

    /**
     * 金額（税込）.
     */
    private BigDecimal amount;

    /**
     * 自由欄１.
     */
    private String free1;

    /**
     * 自由欄２.
     */
    private String free2;

    /**
     * 自由欄３.
     */
    private String free3;

    /**
     * 支払方法.
     */
    private String resPayMethod;

    /**
     * 処理結果ステータス.
     */
    private String resResult;

    /**
     * 処理トラッキングID.
     */
    private String resTrackingId;

    /**
     * 顧客決済情報.
     */
    private String resPayinfoKey;

    /**
     * 完了処理日時.
     */
    private String resPaymentDate;

    /**
     * エラーコード.
     */
    private String resErrCode;

    /**
     * レスポンス日時.
     */
    private String resDate;
}
