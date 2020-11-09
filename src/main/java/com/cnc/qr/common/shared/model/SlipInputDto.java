package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * キッチン印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlipInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * 注文ID.
     */
    private List<Integer> orderIdList;

    /**
     * 小計.
     */
    private BigDecimal subtotal;

    /**
     * お預かり金額.
     */
    private BigDecimal custody;

    /**
     * 值引额.
     */
    private String priceDiscountAmount;

    /**
     * 值引率.
     */
    private String priceDiscountRate;

    /**
     * 外税.
     */
    private BigDecimal sotoTax;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 支払種類.
     */
    private String cashType;

    /**
     * 商品詳細ID.
     */
    private Integer orderDetailId;


    /**
     * 端末.
     */
    private String terminalType;

    /**
     * 出前フラグ.
     */
    private String deliveryFlag;

    /**
     * 会計レシート印刷フラグ（会計レシート後印刷標識 1の場合、印刷する、その他の場合コントロールテーブルにより、印刷する）.
     */
    private Integer accountingPrintFlag;
}
