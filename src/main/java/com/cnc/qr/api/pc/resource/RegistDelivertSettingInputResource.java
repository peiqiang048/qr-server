package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.AreaListOutputDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistDelivertSettingInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;
    
    /**
     * 配達時間.
     */
    @NotBlank
    private String cateringIntervalTime;
    
    /**
     * 持帰り時間.
     */
    @NotBlank
    private String takeoutIntervalTime;
    
    /**
     * 間隔時間.
     */
    @NotBlank
    private String intervalTime;
    
    /**
     * 出前仕方.
     */
    @NotBlank
    private String deliveryTypeFlag;
    
    /**
     * 出前区分.
     */
    @NotBlank
    private String deliveryFlag;
    
    /**
     * 配達料区分.
     */
    @NotBlank
    private String cateringPriceType;
    
    /**
     * 配達料金額.
     */
    @NotBlank
    private String cateringAmount;
    
    /**
     * 配達料パーセント.
     */
    @NotBlank
    private String cateringPercent;
  
    /**
     *エリアリスト.
     */
    @NotNull
    private List<@Valid AreaListOutputDto> areaList;

}
