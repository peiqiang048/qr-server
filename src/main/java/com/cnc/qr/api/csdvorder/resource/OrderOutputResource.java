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
public class OrderOutputResource extends CommonOutputResource {
  
    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    private Integer orderId;
}
