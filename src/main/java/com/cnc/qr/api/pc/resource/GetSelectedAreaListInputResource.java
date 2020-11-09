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
public class GetSelectedAreaListInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * エリア区分.
     */
    @NotBlank
    private String areaType;

    /**
     * 都道府県ID.
     */
    @NotBlank
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 言語.
     */
    @NotBlank
    private String languages;
}
