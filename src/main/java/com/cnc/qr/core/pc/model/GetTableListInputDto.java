package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル一覧情報取得条件.
 */
@Data
public class GetTableListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
}
