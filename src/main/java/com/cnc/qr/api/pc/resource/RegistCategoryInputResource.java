package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
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
public class RegistCategoryInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * カテゴリーID.
     */
    private String categoryId;

    /**
     * カテゴリー名称.
     */
    @NotBlank
    private String categoryName;

    /**
     * グラデーション.
     */
    @NotBlank
    private String gradation;

    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> parentCategoryIdList;
}
