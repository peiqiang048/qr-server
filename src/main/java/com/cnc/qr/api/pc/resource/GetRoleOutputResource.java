package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.ModelInfoDto;
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
public class GetRoleOutputResource extends CommonOutputResource {

    /**
     * 役割名.
     */
    private String roleName;

    /**
     * 選択した機能.
     */
    private List<ModelInfoDto> checkedModelList;

    /**
     * 機能リスト.
     */
    private List<ModelInfoDto> modelList;
}
