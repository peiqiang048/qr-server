package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxValueOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 外税8%值.
     */
    private BigDecimal sotoTaxEight;

    /**
     * 外税10%值.
     */
    private BigDecimal sotoTaxTen;

    /**
     * 内税8%值.
     */
    private BigDecimal utiTaxEight;

    /**
     * 内税10%值.
     */
    private BigDecimal utiTaxTen;

    /**
     * 消费税.
     */
    private BigDecimal tax;

    /**
     * 外税8%对象值.
     */
    private BigDecimal sotoTaxEightSum;

    /**
     * 外税10%对象值.
     */
    private BigDecimal sotoTaxTenSum;

    /**
     * 内税8%对象值.
     */
    private BigDecimal utiTaxEightSum;

    /**
     * 内税10%对象值.
     */
    private BigDecimal utiTaxTensum;

}
