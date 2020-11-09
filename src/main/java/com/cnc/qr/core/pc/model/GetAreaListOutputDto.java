package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * エリア一覧情報取得検索結果.
 */
@Data
public class GetAreaListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * エリアリスト.
     */
    private List<AreaInfoDto> areaList;
}
