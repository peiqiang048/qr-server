package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
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
public class TaxValueOutputResource extends CommonOutputResource {

    /**
     * 外税8%值.
     */
    @NotBlank
    private BigDecimal sotoTaxEight;

    /**
     * 外税10%值.
     */
    @NotBlank
    private BigDecimal sotoTaxTen;

    /**
     * 内税8%值.
     */
    @NotBlank
    private BigDecimal utiTaxEight;

    /**
     * 内税10%值.
     */
    @NotBlank
    private BigDecimal utiTaxTen;

    /**
     * 消费税.
     */
    @NotBlank
    private BigDecimal tax;

    /**
     * 外税8%对象值.
     */
    @NotBlank
    private BigDecimal sotoTaxEightSum;

    /**
     * 外税10%对象值.
     */
    @NotBlank
    private BigDecimal sotoTaxTenSum;

    /**
     * 内税8%对象值.
     */
    @NotBlank
    private BigDecimal utiTaxEightSum;

    /**
     * 内税10%对象值.
     */
    @NotBlank
    private BigDecimal utiTaxTensum;
}
