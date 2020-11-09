package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
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
public class GetCourseOutputResource extends CommonOutputResource {

    /**
     * コース名.
     */
    private String courseName;

    /**
     * コース説明.
     */
    private String courseInfo;

    /**
     * コース単位ID.
     */
    private Integer courseUnitId;

    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

    /**
     * コース単価.
     */
    private String coursePrice;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * コース大きな画像URL.
     */
    private String courseLargePicUrl;

    /**
     * コースサムネイルURL.
     */
    private String courseSmallPicUrl;

    /**
     * 単位リスト.
     */
    private List<GetUnitListOutputDto> unitList;

    /**
     * カテゴリーリスト.
     */
    private List<GetCategoryList> categoryList;

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
}
