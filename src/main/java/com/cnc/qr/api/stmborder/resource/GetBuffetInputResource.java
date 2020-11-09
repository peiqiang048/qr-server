package com.cnc.qr.api.stmborder.resource;

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
public class GetBuffetInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;
    
    /**
     * 语言.
     */
    private String languages;

    /**
     * 受付ID.
     */
    private String receivablesId;
    
    /**
     * 放題ID.
     */
    private String buffetId;
}
