package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
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
public class GetOptionTypeOutputResource extends CommonOutputResource {

    /**
     * オプション種類名.
     */
    private String optionTypeName;

    /**
     * グラデーション.
     */
    private String classification;

    /**
     * 親カテゴリーIDリスト.
     */
    private List<ClassificationInfoDto> classificationList;
}
