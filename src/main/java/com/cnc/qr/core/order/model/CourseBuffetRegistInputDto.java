package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class CourseBuffetRegistInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * テーブルID.
     */
    private Integer tableId;
    
    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 商品リスト.
     */
    private List<OrderBuffetCourseDto> itemList;
    
    /**
     * 注文額.
     */
    private BigDecimal orderAmount;

}
