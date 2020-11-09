package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.GetOptionType;
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
public class GetOptionOutputResource extends CommonOutputResource {

    /**
     * オプション名.
     */
    private String optionName;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;

    /**
     * オプション種類リスト.
     */
    private List<GetOptionType> optionTypeList;
}
