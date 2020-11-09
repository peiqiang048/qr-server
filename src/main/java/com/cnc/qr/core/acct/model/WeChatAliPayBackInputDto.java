package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 先支払い注文状態、支払い状態更新.
 */
@Data
public class WeChatAliPayBackInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文Number.
     */
    private String orderNum;

    /**
     * 支払い結果コード.
     */
    private String respCode;

    /**
     * 備考.
     */
    private String attach;

    /**
     * 金額.
     */
    private String transAmt;

    /**
     * 支払い方式.
     */
    private String paymentBrand;
}
