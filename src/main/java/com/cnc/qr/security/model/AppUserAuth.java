package com.cnc.qr.security.model;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * インプットリソース.
 */
@Data
public class AppUserAuth {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * APP ID.
     */
    private String appId;

    /**
     * APP KEY.
     */
    private String appKey;

    /**
     * 人数.
     */
    private String customerCount;

    /**
     * 受付ID.
     */
    private String receivablesId;
}
