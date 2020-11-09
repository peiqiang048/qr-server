package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.LanguageInfoDto;
import com.cnc.qr.core.pc.model.MenuInfoDto;
import com.cnc.qr.core.pc.model.PicUrlDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetHomePageInfoOutputResource extends CommonOutputResource {

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
