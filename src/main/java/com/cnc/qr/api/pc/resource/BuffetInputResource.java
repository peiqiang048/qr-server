package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class BuffetInputResource {

    /**
     * 店铺ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 放題名.
     */
    private String buffetName;

    /**
     * 状态.
     */
    private String buffetStatus;

    /**
     * 当前语言.
     */
    @NotNull
    private String languages;

    /**
     * 当前页.
     */
    private int page = 0;

    /**
     * 每页条数.
     */
    private int size = 10;

    /**
     * カテゴリーID.
     */
    private String categoryId;
}
