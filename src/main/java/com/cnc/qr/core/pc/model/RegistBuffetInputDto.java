package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * プリンター保存インプット.
 */
@Data
public class RegistBuffetInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 放題ID.
     */
    private Integer buffetId;

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
    private BigDecimal buffetPrice;

    /**
     * 放題時間.
     */
    private Integer buffetTime;

    /**
     * 税区分.
     */
    private Integer buffetTaxId;

    /**
     * 放題カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

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
