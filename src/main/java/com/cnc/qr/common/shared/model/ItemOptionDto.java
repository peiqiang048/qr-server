package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品オプション伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOptionDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * オプション見出し.
     */
    private String optionTitle;

    /**
     * 区分.
     */
    private String classification;
    /**
     * オプション詳細リスト.
     */
    private List<OptionDetailDto> optionDetailList;
}
