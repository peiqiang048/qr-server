package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class EmptyItemInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 削除商品金額.
     */
    private BigDecimal deleteItemAmount;

    /**
     * 注文IDリスト.
     */
    private List<OrderIdDto> orderIdList;
}
