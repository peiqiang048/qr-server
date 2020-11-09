package com.cnc.qr.core.acct.model;

import java.math.BigDecimal;
import lombok.Data;

/**
 * SBペイメント決済結果情報.
 */
@Data
public class SpsResultDto {

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
     * 顧客ID.
     */
    private String customerId;

    /**
     * 決済タイトル.
     */
    private String orderSubject;

    /**
     * 決済通貨.
     */
    private String currencyType;

    /**
     * 決済金額.
     */
    private BigDecimal orderPrice;

    /**
     * 決済状態.
     */
    private String orderStatus;

    /**
     * QR決済機関.
     */
    private String brandType;

    /**
     * 決済開始時間.
     */
    private String orderStartTime;

    /**
     * 決済完了時間.
     */
    private String orderFinishTime;
}
