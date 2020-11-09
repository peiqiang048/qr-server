package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * エリア情報取得検索結果.
 */
@Data
public class AreaTypeOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * エリア情報.
     */
    private List<AreaTypeDto> areaTypeList;

    /**
     * 店舗名.
     */
    private String storeName;
}
