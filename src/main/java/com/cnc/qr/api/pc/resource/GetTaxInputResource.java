package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 税設定編集インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaxInputResource {

    /**
     * ビジネスID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String businessId;


    /**
     * 税ID.
     */
    private String taxId;
}
