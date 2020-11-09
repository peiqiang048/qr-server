package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 会計PAD商品回数.
 */
@Data
public class OrderItemNumDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文回数.
     */
    private List<ItemsDetailDto> orderNumber;
}
