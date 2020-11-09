package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品オプション情報取得検索結果.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOptionType implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
    
    /**
     * オプション種類名称.
     */
    private String optionTypeName;
}
