package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 指定区域取得検索結果.
 */
@Data
public class GetSelectedAreaListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * エリアリスト.
     */
    private List<GetAreaInfoDto> areaList;
}
