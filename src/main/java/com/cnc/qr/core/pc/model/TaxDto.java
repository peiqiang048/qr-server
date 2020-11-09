package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 税設定情報取得検索結果.
 */
@Data
public class TaxDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 税ID.
     */
    private String taxId;

    /**
     * 税設定名称.
     */
    private String taxName;

    /**
     * 適用区分コード.
     */
    private String taxReliefApplyTypeCode;

    /**
     * 適用区分名.
     */
    private String taxReliefApplyTypeName;

    /**
     * 適用日時.
     */
    private String applyDate;
}
