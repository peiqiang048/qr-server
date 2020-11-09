package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 商品ID.
     */
    private Integer itemId;

    /**
     * 税ID.
     */
    private Integer taxId;

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
    private BigDecimal taxRateNormal;

    /**
     * 轻减税率.
     */
    private BigDecimal taxRateRelief;

}
