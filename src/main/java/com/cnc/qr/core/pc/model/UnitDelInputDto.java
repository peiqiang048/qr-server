package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 单位削除情報取得条件.
 */
@Data
public class UnitDelInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 单位IDリスト.
     */
    private List<UnitInputDto> unitList;

}
