package com.cnc.qr.core.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsTempDto {

    /**
     * 商品ID.
     */
    public String itemId;

    /**
     * 商品单价.
     */
    public Long itemIdPriceSum;
}
