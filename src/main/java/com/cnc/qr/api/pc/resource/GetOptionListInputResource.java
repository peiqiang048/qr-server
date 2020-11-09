package com.cnc.qr.api.pc.resource;

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
public class GetOptionListInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;

    /**
     * オプション名称.
     */
    private String optionName;

    /**
     * 言語.
     */
    @NotBlank
    private String languages;

    /**
     * ページ.
     */
    private int page = 0;

    /**
     * ページサイズ.
     */
    private int size = 10;
}
