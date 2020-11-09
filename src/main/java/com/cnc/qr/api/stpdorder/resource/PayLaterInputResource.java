package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
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
public class PayLaterInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付ID.
     */
    @NotBlank
    private String receivablesId;

    /**
     * 支付价格.
     */
    @NotBlank
    private String payAmount;

    /**
     * 二维码CD.
     */
    @NotBlank
    private String payCodeId;

    /**
     * 就餐区分.
     */
    @NotBlank
    private String takeoutFlag;

    /**
     * 值引额.
     */
    private String priceDiscountAmount;

    /**
     * 值引率.
     */
    private String priceDiscountRate;

    /**
     * 外税.
     */
    private String sotoTax;


    /**
     * 小計.
     */
    private String subtotal;
}
