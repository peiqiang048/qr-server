package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 店舗言語情報取得検索結果.
 */
@Data
public class GetActiveLanguageOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 言語リスト.
     */
    private List<StoreLanguageDto> languages;
}
