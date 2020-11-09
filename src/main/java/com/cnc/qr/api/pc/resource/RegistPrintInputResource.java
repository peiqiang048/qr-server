package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * プリンター編集確定インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistPrintInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;
    
    /**
     * プリンターID.
     */
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String printId;
    
    /**
     * プリンター名.
     */
    @NotBlank
    private String printName;
    
    /**
     * プリンタブランドCD.
     */
    @NotBlank
    private String brandCode;
    
    /**
     * 接続方法CD.
     */
    @NotBlank
    private String connectionMethodCode;
    
    /**
     * プリンターIP.
     */
    private String printIp;
    
    /**
     * ブルートゥース名.
     */
    private String blueToothName;
    
    /**
     * モデル.
     */
    private String printModel;
    
    /**
     * チケット幅CD.
     */
    @NotBlank
    private String printSizeCode;



}
