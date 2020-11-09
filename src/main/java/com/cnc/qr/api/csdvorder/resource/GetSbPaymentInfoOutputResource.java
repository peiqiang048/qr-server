package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetSbPaymentInfoOutputResource extends CommonOutputResource {

    /**
     * ペイメントUrl.
     */
    private String actionUrl;

    /**
     * 支払方法.
     */
    private String payMethod;

    /**
     * マーチャントID.
     */
    private String merchantId;

    /**
     * サービスID.
     */
    private String serviceId;

    /**
     * 顧客ID.
     */
    private String custCode;

    /**
     * 購入ID.
     */
    private String orderId;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 金額（税込）.
     */
    private String amount;

    /**
     * 購入タイプ.
     */
    private String payType;

    /**
     * サービスタイプ.
     */
    private String serviceType;

    /**
     * 顧客利用端末タイプ.
     */
    private String terminalType;

    /**
     * 決済完了時URL.
     */
    private String successUrl;

    /**
     * 決済キャンセル時URL.
     */
    private String cancelUrl;

    /**
     * エラー時URL.
     */
    private String errorUrl;

    /**
     * 決済通知用CGI.
     */
    private String pageconUrl;

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
     * フリー項目.
     */
    private String freeCsv;

    /**
     * リクエスト日時 .
     */
    private String requestDate;

    /**
     * リクエスト許容時間.
     */
    private String limitSecond;

    /**
     * ハッシュキー.
     */
    private String hashKey;

    /**
     * チェックサム.
     */
    private String spsHashcode;
}
