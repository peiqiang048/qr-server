package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * シーケンスNo取得検索条件.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSeqNoInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 項目.
     */
    private String item;

    /**
     * 登録更新者.
     */
    private String operCd;
}
