package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品オプション情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOption implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 商品オプションコード.
     */
    private String optionCode;

    /**
     * 商品オプション名.
     */
    private String optionName;

    /**
     * 商品オプション種類コード.
     */
    private String optionTypeCd;
}
