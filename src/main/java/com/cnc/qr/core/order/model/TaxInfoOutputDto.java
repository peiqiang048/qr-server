package com.cnc.qr.core.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 商品ID.
     */
    private String itemId;

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
