package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * オプション情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOptionOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * オプション名.
     */
    private String optionName;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;

    /**
     * オプション種類リスト.
     */
    private List<GetOptionType> optionTypeList;
}
