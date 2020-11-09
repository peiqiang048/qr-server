package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 商品オプション種類情報取得検索結果.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOptionTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品オプション種類ID.
     */
    private String optionTypeCd;

    /**
     * 商品オプション種類名.
     */
    private String optionTypeName;

    /**
     * 商品オプション.
     */
    private List<GetOptionDto> optionList;
}
