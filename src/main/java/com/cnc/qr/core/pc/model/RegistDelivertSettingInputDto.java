package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class RegistDelivertSettingInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * 配達時間.
     */
    private Integer cateringIntervalTime;
    
    /**
     * 持帰り時間.
     */
    private Integer takeoutIntervalTime;
    
    /**
     * 間隔時間.
     */
    private Integer intervalTime;
    
    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;
    
    /**
     * 出前区分.
     */
    private String deliveryFlag;
    
    /**
     * 配達料区分.
     */
    private String cateringPriceType;
    
    /**
     * 配達料金額.
     */
    private BigDecimal cateringAmount;
    
    /**
     * 配達料パーセント.
     */
    private BigDecimal cateringPercent;
  
    /**
     *エリアリスト.
     */
    private List<AreaListOutputDto> areaList;
}
