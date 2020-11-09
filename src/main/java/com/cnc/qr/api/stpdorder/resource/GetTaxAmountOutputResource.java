package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
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
public class GetTaxAmountOutputResource extends CommonOutputResource {

    /**
     * 小計.
     */
    private BigDecimal orderAmount;

    /**
     * 外税.
     */
    private BigDecimal foreignTax;

    /**
     * 合計.
     */
    private BigDecimal total;
}
