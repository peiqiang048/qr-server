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
 * コース編集確定インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistCourseInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * コースID.
     */
    private Integer courseId;

    /**
     * コース名称.
     */
    @NotBlank
    private String courseName;

    /**
     * コース说明.
     */
    private String courseInfo;

    /**
     * コース单位.
     */
    @NotNull
    private Integer courseUnitId;

    /**
     * コースカテゴリーIDリスト.
     */
    private List<@Valid GetCategoryIdListOutputDto> categoryIdList;

    /**
     * コース单价.
     */
    @NotNull
    private BigDecimal coursePrice;

    /**
     * コース税区分.
     */
    @NotNull
    private Integer courseTaxId;

    /**
     * 商品リスト.
     */
    private List<ItemListDto> itemList;

    /**
     * 商品大图url.
     */
    private String itemLargePicUrl;

    /**
     * 商品缩略图url.
     */
    private String itemSmallPicUrl;


}
