package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * プリンター情報取得検索結果.
 */
@Data
public class UnitListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 单位情報.
     */
    private List<UnitDto> unitList;

    /**
     * 数据条数.
     */
    private Long totalCount;
}
