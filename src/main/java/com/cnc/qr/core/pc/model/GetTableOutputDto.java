package com.cnc.qr.core.pc.model;

import com.cnc.qr.core.order.model.GetTableTypeInfoDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * テーブル情報取得検索結果.
 */
@Data
public class GetTableOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 席位数.
     */
    private Integer tableSeatCount;

    /**
     * エリアID.
     */
    private Integer areaId;

    /**
     * 席種類.
     */
    private String tableType;

    /**
     * エリアリスト.
     */
    private List<AreaInfoDto> areaList;

    /**
     * 席種類リスト.
     */
    private List<GetTableTypeInfoDto> tableTypeList;

}
