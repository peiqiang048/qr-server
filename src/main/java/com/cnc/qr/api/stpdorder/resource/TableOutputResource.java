package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.MenuDto;
import com.cnc.qr.core.order.model.OrderDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class TableOutputResource extends CommonOutputResource {

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * エリア名.
     */
    private String areaTypeName;

    /**
     * テーブル上限客数.
     */
    private String tableSeatCount;

    /**
     * 客数.
     */
    private String customerCount;

    /**
     * オーダー数.
     */
    private String orderCount;

    /**
     * オーダーリスト.
     */
    private List<OrderDto> orderList;

}
