package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 注文未確認商品一覧情報取得結果.
 */
@Data
public class GetUnCfmOrderItemListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 未確認金額.
     */
    private BigDecimal unconfirmedAmount;

    /**
     * 未確認商品リスト.
     */
    private List<UnCfmItemInfoDto> itemList;
}
