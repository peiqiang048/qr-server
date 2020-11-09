package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * コース保存インプット.
 */
@Data
public class RegistCourseInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * コースID.
     */
    private Integer courseId;

    /**
     * コース名称.
     */
    private String courseName;

    /**
     * コース说明.
     */
    private String courseInfo;

    /**
     *コース单位.
     */
    private Integer courseUnitId;

    /**
     * コースカテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

    /**
     * コース单价.
     */
    private BigDecimal coursePrice;

    /**
     * コース税区分.
     */
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
