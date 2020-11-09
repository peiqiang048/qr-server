package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.AreaListDto;
import com.cnc.qr.core.pc.model.BlockListDto;
import com.cnc.qr.core.pc.model.CateringPriceTypeListDto;
import com.cnc.qr.core.pc.model.CityListDto;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.PrefectureListDto;
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
public class GetDeliverySettingOutputResource extends CommonOutputResource {

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
