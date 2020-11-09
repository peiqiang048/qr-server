package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 厨房明細伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenDetailsDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 商品リスト.
     */
    private List<KitchenItemDto> itemList;
}
