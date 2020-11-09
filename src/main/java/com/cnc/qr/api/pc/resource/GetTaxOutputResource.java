package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.TaxReliefApplyTypeDto;
import com.cnc.qr.core.pc.model.TaxRoundDto;
import com.cnc.qr.core.pc.model.TaxTypeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetTaxOutputResource extends CommonOutputResource {

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
