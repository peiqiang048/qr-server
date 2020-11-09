package com.cnc.qr.api.csmborder.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.cnc.qr.common.constants.RegexConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOrderItemInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付番号.
     */
    @NotBlank
    private String receivablesId;

    /**
     * 検索区分.
     */
    @NotBlank
    private String itemBuffetType;
}
