package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 未確認商品情報.
 */
@Data
public class UnCfmItemInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 注文明細ID.
     */
    private String orderDetailId;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品金額.
     */
    private BigDecimal itemPrice;

    /**
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * 商品オプション情報.
     */
    private String itemOption;
}
