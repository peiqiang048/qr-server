package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * テーブル一覧情報取得検索結果.
 */
@Data
public class GetAreaTableListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * テーブル情報リスト.
     */
    private List<TableListDto> tableList;
}
