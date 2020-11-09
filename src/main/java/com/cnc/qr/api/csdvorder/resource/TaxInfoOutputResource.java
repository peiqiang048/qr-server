package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class TaxInfoOutputResource extends CommonOutputResource {

    /**
     * 税ID.
     */
    private String taxId;

    /**
     * 税区分.
     */
    private String taxCode;

    /**
     * 端数处理区分.
     */
    private String taxRoundType;

    /**
     * 轻减税率适用区分.
     */
    private String taxReliefApplyType;

    /**
     * 标准税率.
     */
    private String taxRateNormal;

    /**
     * 轻减税率.
     */
    private String taxRateRelief;

}
