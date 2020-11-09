package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * キッチン情報取得検索結果.
 */
@Data
public class KitchenListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * キッチン情報.
     */
    private List<KitchenDto> kitchenList;
}
