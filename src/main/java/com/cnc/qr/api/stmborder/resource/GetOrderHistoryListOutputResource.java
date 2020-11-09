package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.OrderItemInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetOrderHistoryListOutputResource extends CommonOutputResource {

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
