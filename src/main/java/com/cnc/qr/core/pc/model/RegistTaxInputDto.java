package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 税設定保存インプット.
 */
@Data
public class RegistTaxInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * 税ID.
     */
    private Integer taxId;

    /**
     * 税設定名称.
     */
    private String taxName;

    /**
     * 税区分.
     */
    private String taxCode;

    /**
     * 税端数処理区.
     */
    private String taxRoundType;

    /**
     * 軽減税率適用区分.
     */
    private String taxReliefApplyType;

    /**
     * 標準税率.
     */
    private Integer taxRateNormal;

    /**
     * 軽減税率.
     */
    private Integer taxRateRelief;

    /**
     * 適用日時.
     */
    private String applyDate;

}
