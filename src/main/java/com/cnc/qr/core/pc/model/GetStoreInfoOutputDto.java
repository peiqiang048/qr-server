package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 店舗情報取得結果.
 */
@Data
public class GetStoreInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗言語.
     */
    private List<LanguageInfoDto> languages;

    /**
     * 当前店铺最大分类层数.
     */
    private String maxGradation;
}
