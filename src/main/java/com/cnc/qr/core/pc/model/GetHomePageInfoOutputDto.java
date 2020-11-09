package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ホームページ情報取得結果.
 */
@Data
public class GetHomePageInfoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗Logo Url.
     */
    private String storeLogoUrl;

    /**
     * 店舗言語.
     */
    private List<LanguageInfoDto> languages;

    /**
     * メニュー.
     */
    private List<MenuInfoDto> menuList;

    /**
     * 店舗画像.
     */
    private List<PicUrlDto> picList;
}
