package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlipDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

    /**
     * テイクアウト区分.
     */
    private String takeoutFlag;

    /**
     * 値引額.
     */
    private BigDecimal priceDiscountAmount;

    /**
     * 値引率.
     */
    private BigDecimal priceDiscountRate;

    /**
     * 支払額.
     */
    private BigDecimal paymentAmount;

    /**
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 備考.
     */
    private String comment;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 注文明細ID.
     */
    private Integer orderDetailId;
    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 単価.
     */
    private BigDecimal itemPrice;

    /**
     * 商品個数.
     */
    private Integer itemCount;
    /**
     * 商品区分.
     */
    private String itemClassification;
    /**
     * 返品元注文明細ID.
     */
    private Integer returnOrderDetailId;

    /**
     * 返品原因ID.
     */
    private String itemReturnId;
    /**
     * オプション種類名.
     */
    private String optionTypeName;

    /**
     * 商品オプション種類コード.
     */
    private String itemOptionTypeCode;


    /**
     * 商品オプションコード.
     */
    private String itemOptionCode;


    /**
     * 商品オプション数量.
     */
    private Integer itemOptionCount;


    /**
     * オプション名.
     */
    private String optionName;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品区分.
     */
    private String itemType;

    /**
     * オプション有フラグ.
     */
    private String optionFlag;
    /**
     * キチン名.
     */
    private String kitchenName;

    /**
     * プリンタip.
     */
    private String printIp;

    /**
     * ブルートゥース名.
     */
    private String bluetoothName;

    /**
     * プリンタブランドCD,.
     */
    private String brandCode;

    /**
     * 接続方式CD.
     */
    private String connectionMethodCode;

    /**
     * 伝票幅CD.
     */
    private String printSize;

    /**
     * テーブル名.
     */
    private String tableName;
    /**
     * 区分.
     */
    private String classification;
    /**
     * 税コード.
     */
    private String taxCode;

    /**
     * 軽減税率適用区分.
     */
    private String taxReliefApplyType;


    /**
     * 受付番号.
     */
    private Integer receptionNo;
}
