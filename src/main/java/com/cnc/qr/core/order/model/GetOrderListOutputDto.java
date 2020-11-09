package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 注文一覧情報取得検索結果.
 */
@Data
public class GetOrderListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文総件数.
     */
    private long orderCount;

    /**
     * 注文一覧情報.
     */
    private Page<OrderInfoDto> orderList;
}
