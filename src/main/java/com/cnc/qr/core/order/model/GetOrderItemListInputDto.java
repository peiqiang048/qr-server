package com.cnc.qr.core.order.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 注文商品リスト取得条件.
 */
@Data
public class GetOrderItemListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 言語.
     */
    private String languages;
}
