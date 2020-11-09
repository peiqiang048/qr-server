package com.cnc.qr.common.shared.model;

import com.cnc.qr.core.order.model.ItemOptionTypeDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品オプション類型情報取得検索結果.
 */
@Data
public class GetItemOptionInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプション類型情報.
     */
    private List<ItemOptionTypeDto> optionTypeList;
}
