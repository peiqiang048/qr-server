package com.cnc.qr.core.acct.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 飲食区分取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRefundsListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文番号.
     */
    private String orderNo;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;

    /**
     * 支払コード.
     */
    private String paymentCode;

    /**
     * 支払時間.
     */
    private String payTime;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付番号.
     */
    private Integer receivablesNo;

    /**
     * テーブル名称.
     */
    private String tableName;

    /**
     * 支払方法.
     */
    private String paymentMethod;
}
