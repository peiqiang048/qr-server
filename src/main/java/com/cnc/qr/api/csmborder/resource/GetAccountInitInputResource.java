package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountInitInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付ID.
     */
    @NotBlank
    private String receivablesId;

    /**
     * 语言.
     */
    @NotBlank
    private String languages;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 支払区分.
     */
    @NotBlank
    private String paymentDistinction;

}
