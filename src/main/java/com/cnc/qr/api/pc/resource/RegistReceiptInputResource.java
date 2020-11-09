package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レシート編集確定インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistReceiptInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;
    
    /**
     * 票据ID.
     */
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String receiptId;
      
    /**
     * プリンターID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String printId;
    
    /**
     * 票据名.
     */
    @NotBlank
    private String receiptName;

}
