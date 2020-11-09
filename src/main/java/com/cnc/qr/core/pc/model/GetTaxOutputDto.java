package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 税設定編集取得検索結果.
 */
@Data
public class GetTaxOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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

    /**
     * 税区分リスト.
     */
    private List<TaxTypeDto> taxTypeList;

    /**
     * 税端数処理区リスト.
     */
    private List<TaxRoundDto> taxRoundTypeList;

    /**
     * 軽減税率適用区分リスト.
     */
    private List<TaxReliefApplyTypeDto> taxReliefApplyTypeList;

}
