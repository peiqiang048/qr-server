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
public class GetItemOptionTypeInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 言語.
     */
    @NotBlank
    private String languages;

    /**
     * 商品ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String itemId;
}
