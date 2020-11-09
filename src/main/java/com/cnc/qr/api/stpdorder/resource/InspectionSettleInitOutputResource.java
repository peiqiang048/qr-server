package com.cnc.qr.api.stpdorder.resource;

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
public class InspectionSettleInitOutputResource extends CommonOutputResource {

    /**
     * 前日繰越金.
     */
    private String previousDayTransferredAmount;

    /**
     * 本日増減額.
     */
    private String todayFluctuationAmount;

    /**
     * 点検精算日.
     */
    private String inspectionSettleDate;

}
