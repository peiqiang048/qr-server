package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * テーブル一覧情報取得結果.
 */
@Data
public class GetTableListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブルリスト.
     */
    private List<TableInfoDto> tableList;

    /**
     * 数据条数.
     */
    private Long totalCount;
}
