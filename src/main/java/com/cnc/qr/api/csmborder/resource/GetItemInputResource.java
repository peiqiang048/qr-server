package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import java.util.List;
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
public class GetItemInputResource {

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
     * 商品カテゴリー.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String categoryId;

    /**
     * 検索内容.
     */
    private String searchInfo;

    /**
     * 放題ID.
     */
    private String buffetId;

    /**
     * コースフラグ.
     */
    private String courseFlag;

    /**
     * 放題ID.
     */
    private List<Integer> buffetIdList;
}
