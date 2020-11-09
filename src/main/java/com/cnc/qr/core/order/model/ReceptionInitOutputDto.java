package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 受付選択可能テーブル情報取得検索結果.
 */
@Data
public class ReceptionInitOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;


    /**
     * 受付テーブルリスト.
     */
    private List<ReceptionTableDto> tableList;

}
