package com.cnc.qr.api.csmborder.resource;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbPaymentCallBackInputResource {

    /**
     * 購入ID.
     */
    @NotBlank
    private String orderId;

    /**
     * 金額（税込）.
     */
    @NotBlank
    private String amount;

    /**
     * 自由欄１.
     */
    @NotBlank
    private String free1;

    /**
     * 自由欄２.
     */
    @NotBlank
    private String free2;

    /**
     * 自由欄３.
     */
    @NotBlank
    private String free3;

    /**
     * 支払方法.
     */
    @NotBlank
    private String resPayMethod;

    /**
     * 処理結果ステータス.
     */
    @NotBlank
    private String resResult;

    /**
     * 処理トラッキングID.
     */
    @NotBlank
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
    @NotBlank
    private String resDate;
}
