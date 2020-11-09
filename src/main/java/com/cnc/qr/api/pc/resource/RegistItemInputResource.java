package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.RegistItemOptionDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistItemInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名称.
     */
    @NotBlank
    private String itemName;

    /**
     * 商品説明.
     */
    private String itemInfo;

    /**
     * 商品単位ID.
     */
    @NotBlank
    private String itemUnitId;

    /**
     * カテゴリーIDリスト.
     */
    @NotNull
    private List<@Valid GetCategoryIdListOutputDto> categoryIdList;

    /**
     * 商品単価.
     */
    @NotBlank
    private String itemPrice;

    /**
     * 商品税ID.
     */
    @NotNull
    private Integer itemTaxId;

    /**
     * 商品キチンID.
     */
    @NotBlank
    private String itemKitchenId;

    /**
     * 商品大きな画像URL.
     */
    private String itemLargePicUrl;

    /**
     * 商品サムネイルURL.
     */
    private String itemSmallPicUrl;

    /**
     * 商品選択したオプション.
     */
    private List<RegistItemOptionDto> itemOptionList;

    /**
     * 出前区分.
     */
    private String deliveryFlag;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 商品ビデオURL.
     */
    private String itemVideo;
}
