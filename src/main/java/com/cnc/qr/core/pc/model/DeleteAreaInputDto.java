package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * エリア情報削除データ.
 */
@Data
public class DeleteAreaInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * エリアIDリスト.
     */
    private List<AreaIdDto> areaList;
}
