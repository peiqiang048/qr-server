package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文確認データ.
 */
@Data
public class SureOrderItemInputDto implements Serializable {

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
     * 確認注文IDリスト.
     */
    private List<Integer> orderIdList;
    /**
     * 変更後の商品数.
     */
    private List<UnCfmItemInfoDto> itemList;

    /**
     * 小計.
     */
    private BigDecimal unconfirmedAmount;

}
