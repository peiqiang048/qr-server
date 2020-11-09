package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出前設定取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDeliverySettingOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 配達時間.
     */
    private String cateringIntervalTime;
    
    /**
     * 持帰り時間.
     */
    private String takeoutIntervalTime;
    
    /**
     * 間隔時間.
     */
    private String intervalTime;
    
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
    private String cateringAmount;
    
    /**
     * 配達料パーセント.
     */
    private String cateringPercent;
    
    /**
     * 都道府県リスト.
     */
    private List<PrefectureListDto> prefectureList;
    
    /**
     * 市区町村リスト.
     */
    private List<CityListDto> cityList;
    
    /**
     * 町域番地リスト.
     */
    private List<BlockListDto> blockList;
    
    /**
     * エリアリスト.
     */
    private List<AreaListDto> areaList;
    
    /**
     * 出前区分リスト.
     */
    private List<DeliveryFlagListDto> deliveryFlagList;
    
    /**
     * 出前仕方リスト.
     */
    private List<DeliveryTypeFlagListDto> deliveryTypeFlagList;
    
    /**
     * 配達料区分リスト.
     */
    private List<CateringPriceTypeListDto> cateringPriceTypeList;
}
