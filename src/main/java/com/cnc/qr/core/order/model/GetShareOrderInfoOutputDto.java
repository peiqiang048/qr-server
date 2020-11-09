package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 注文情報取得検索結果.
 */
@Data
public class GetShareOrderInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * バージョン.
     */
    private String version;

    /**
     * 客の商品情報リスト.
     */
    private List<NoItemsInfoDto> noItems;
}
