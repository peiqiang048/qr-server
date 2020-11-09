package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 飲み放題編集確定インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistBuffetInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 放題ID.
     */
    private Integer buffetId;

    /**
     * 放題名称.
     */
    @NotBlank
    private String buffetName;

    /**
     * 放題说明.
     */
    private String buffetInfo;

    /**
     * 放題单价.
     */
    @NotNull
    private BigDecimal buffetPrice;

    /**
     * 放題時間.
     */
    private Integer buffetTime;

    /**
     * 税区分.
     */
    @NotNull
    private Integer buffetTaxId;

    /**
     * カテゴリーIDリスト.
     */
    private List<@Valid GetCategoryIdListOutputDto> categoryIdList;

    /**
     * 附加商品ID.
     */
    private Integer attachItemId;

    /**
     * 商品リスト.
     */
    private List<ItemListDto> selectedItemList;

    /**
     * 商品大图url.
     */
    private String itemLargePicUrl;

    /**
     * 商品缩略图url.
     */
    private String itemSmallPicUrl;


}
