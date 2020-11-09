package com.cnc.qr.core.acct.model;

import java.math.BigDecimal;
import lombok.Data;

/**
 * SBペイメント返金要求結果情報.
 */
@Data
public class SpsRefundResultDto {

    /**
     * QR端末返金処理通番.
     */
    private String clientRefundNo;

    /**
     * QRGW返金処理通番.
     */
    private String gwRefundNo;

    /**
     * QR決済機関返金番号.
     */
    private String brandRefundNo;

    /**
     * QR端末処理通番.
     */
    private String clientOrderNo;

    /**
     * QRGW処理通番.
     */
    private String gwOrderNo;

    /**
     * QR決済機関番号.
     */
    private String brandOrderNo;

    /**
     * 決済金額.
     */
    private BigDecimal orderPrice;

    /**
     * 返金開始時間.
     */
    private String refundStartTime;

    /**
     * 返金完了時間.
     */
    private String refundFinishTime;

    /**
     * 決済通貨.
     */
    private String currencyType;

    /**
     * 返金金額.
     */
    private BigDecimal refundPrice;

    /**
     * 返金状態.
     */
    private String refundStatus;
}
