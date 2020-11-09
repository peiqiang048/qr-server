package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;
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
public class GetBuffetOutputResource extends CommonOutputResource {

    /**
     * 放題名称.
     */
    private String buffetName;

    /**
     * 放題说明.
     */
    private String buffetInfo;

    /**
     * 放題单价.
     */
    private String buffetPrice;

    /**
     * 放題時間.
     */
    private String buffetTime;

    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

    /**
     * カテゴリーリスト.
     */
    private List<GetCategoryList> categoryList;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * コース大きな画像URL.
     */
    private String buffetLargePicUrl;

    /**
     * コースサムネイルURL.
     */
    private String buffetSmallPicUrl;

    /**
     * 税リスト.
     */
    private List<GetTaxListOutputDto> taxList;

    /**
     * 商品リスト.
     */
    private List<ItemListDto> itemList;

    /**
     * カテゴリーリスト.
     */
    private List<GetCategoryList> hasItemCategoryList;

    /**
     * 商品リスト.
     */
    private List<ItemListDto> selectedItemList;

    /**
     * 附加商品ID.
     */
    private String attachItemId;
}
