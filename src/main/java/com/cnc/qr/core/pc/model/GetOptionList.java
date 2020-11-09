package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品オプション情報取得検索結果.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOptionList implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 商品オプションID.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;

    /**
     * オプション種類名称.
     */
    private String optionTypeName;

    /**
     * オプション順番.
     */
    private String optionSortOrder;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
}
