package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaxListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 税コード.
     */
    private Integer taxId;

    /**
     * 税名.
     */
    private String taxName;
}
