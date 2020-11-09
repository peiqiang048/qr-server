package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import java.math.BigDecimal;
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
public class RegistPaymentInputResource {

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
     * 值引额.
     */
    private String priceDiscountAmount;

    /**
     * 值引率.
     */
    private String priceDiscountRate;

    /**
     * 支払い金額.
     */
    @NotBlank
    private String paymentAmount;

    /**
     * 就餐区分.
     */
    @NotBlank
    private String takeoutFlag;

    /**
     * 外税.
     */
    @NotNull
    private String sotoTax;

    /**
     * 小計.
     */
    @NotNull
    private String subtotal;

    /**
     * 支払い方式コード.
     */
    @NotNull
    private String paymentMethodCode;

    /**
     * お預かり金額.
     */
    @NotNull
    private String custody;
}
