package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 商品カテゴリー情報取得検索結果.
 */
@Data
public class CourseBuffetDetailOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * コース＆放题名称.
     */
    private String courseBuffetName;
    
    /**
     * コース＆放题金额.
     */
    private BigDecimal courseBuffetPrice;
    
    /**
     * 税区分.
     */
    private String taxCode;
    
    /**
     * コース商品紹介.
     */
    private String courseIntroduction;
    
    /**
     * コース商品.
     */
    private List<ItemDto> courseItemList;
    
    /**
     * 放題商品紹介.
     */
    private String buffetIntroduction;
    
    /**
     * 放題商品.
     */
    private List<ItemDto> buffetItemList;
}
