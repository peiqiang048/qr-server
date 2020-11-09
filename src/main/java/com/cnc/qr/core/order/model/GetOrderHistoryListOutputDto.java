package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文履歴情報取得結果.
 */
@Data
public class GetOrderHistoryListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文金額.
     */
    private BigDecimal orderAmount;

    /**
     * 注文履歴情報.
     */
    private List<OrderItemInfoDto> orderHistoryList;

    /**
     * 店員会計可能フラグ（店員用スマホ）.
     */
    private Integer staffAccountAbleFlag;

    /**
     * 受付番号.
     */
    private String receptionNo;

    /**
     * テーブル名.
     */
    private String tableName;
}
