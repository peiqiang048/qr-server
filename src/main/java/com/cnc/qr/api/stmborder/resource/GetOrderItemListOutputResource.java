package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.UnCfmItemInfoDto;
import com.cnc.qr.core.order.model.UnCfmOrderInfoDto;
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
public class GetOrderItemListOutputResource extends CommonOutputResource {

    /**
     * 未確認金額.
     */
    private BigDecimal unconfirmedAmount;

    /**
     * 未確認商品リスト.
     */
    private List<UnCfmItemInfoDto> itemList;

}
