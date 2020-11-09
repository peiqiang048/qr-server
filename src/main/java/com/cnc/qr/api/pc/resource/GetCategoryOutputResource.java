package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetCategoryOutputResource extends CommonOutputResource {

    /**
     * カテゴリー名.
     */
    private String categoryName;

    /**
     * グラデーション.
     */
    private String gradation;

    /**
     * 親カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> parentCategoryIdList;

    /**
     * 親カテゴリーリスト.
     */
    private List<GetCategoryList> parentCategoryList;

    /**
     * MAXグラデーション.
     */
    private Integer maxGradation;
}
