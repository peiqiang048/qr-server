package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.AreaTypeDto;
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
public class GetAreaTypeListOutputResource extends CommonOutputResource {

    /**
     * エリア类型リスト.
     */
    private List<AreaTypeDto> areaTypeList;
}
