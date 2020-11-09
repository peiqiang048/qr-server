package com.cnc.qr.api.stmborder.resource;

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
public class GetItemListInputResource {

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
     * 受付ID.
     */
    private String receivablesId;

    /**
     * ページ.
     */
    private int page = 0;

    /**
     * ページサイズ.
     */
    private int pageCount = 5;

    /**
     * 放題ID.
     */
    private List<Integer> buffetIdList;
}
