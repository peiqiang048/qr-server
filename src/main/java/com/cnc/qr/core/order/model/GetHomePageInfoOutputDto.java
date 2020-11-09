package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * トップ画面情報取得結果.
 */
@Data
public class GetHomePageInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗営業時間.
     */
    private String storeTime;

    /**
     * 店員確認フラグ.
     */
    private String staffCheckFlag;

    /**
     * 店舗言語.
     */
    private List<StoreLanguageDto> languageList;


    /**
     * 未確認の件数.
     */
    private Integer unCofimCount;
}
