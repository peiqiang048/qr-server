package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.DeliveryOrderInfoDto;
import com.cnc.qr.core.order.model.DeliveryStatusDto;
import com.cnc.qr.core.order.model.DeliveryTypeFlagDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
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
public class GetDeliveryOrderListOutputResource extends CommonOutputResource {

    /**
     * 出前注文総件数.
     */
    private long deliveryOrderCount;

    /**
     * 出前仕方リスト.
     */
    private List<DeliveryTypeFlagDto> deliveryTypeFlagList;

    /**
     * 状態リスト.
     */
    private List<DeliveryStatusDto> statusList;

    /**
     * 出前注文一覧情報.
     */
    private List<DeliveryOrderInfoDto> deliveryOrderList;
}
