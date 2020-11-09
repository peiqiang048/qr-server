package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 出前注文一覧情報取得検索結果.
 */
@Data
public class GetDeliveryOrderListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

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
    private Page<DeliveryOrderInfoDto> deliveryOrderList;
}
