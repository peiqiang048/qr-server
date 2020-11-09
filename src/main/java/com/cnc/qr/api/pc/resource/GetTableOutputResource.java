package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.GetTableTypeInfoDto;
import com.cnc.qr.core.pc.model.AreaInfoDto;
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
public class GetTableOutputResource extends CommonOutputResource {

    /**
     * テーブル名.
     */
    private String tableName;

    /**
     * 席位数.
     */
    private String tableSeatCount;

    /**
     * エリアID.
     */
    private String areaId;

    /**
     * 席種類.
     */
    private String tableType;

    /**
     * エリアリスト.
     */
    private List<AreaInfoDto> areaList;

    /**
     * 席種類リスト.
     */
    private List<GetTableTypeInfoDto> tableTypeList;
}
