package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 商品ID.
     */
    private String itemId;
    /**
     * 商品名称.
     */
    private String itemName;
    /**
     * 商品個数.
     */
    private String itemCount;

}
