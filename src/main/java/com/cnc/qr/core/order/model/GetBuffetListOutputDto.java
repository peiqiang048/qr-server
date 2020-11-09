package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品オプション種類情報取得検索結果.
 */
@Data
public class GetBuffetListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 放題情報.
     */
    private List<BuffetListDto> buffetList;
}
