package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品オプション情報取得検索結果.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOptionDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品オプションID.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;
    
    /**
     * 選択区分.
     */
    private Integer checkedFlag;
    
    /**
     * option差价.
     */
    private BigDecimal itemOptionDiffPrice;
}
