package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 税設定編集確定インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistTaxInputResource {

    /**
     * ビジネスID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String businessId;

    /**
     * 税ID.
     */
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String taxId;

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
