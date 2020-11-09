package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 店舗情報取得検索結果.
 */
@Data
public class GetStoreListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗情報.
     */
    private List<StoreDto> storeList;
}
