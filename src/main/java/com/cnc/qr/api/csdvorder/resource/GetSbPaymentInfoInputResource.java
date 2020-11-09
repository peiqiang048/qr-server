package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
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
public class GetSbPaymentInfoInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 注文サマリID.
     */
    @NotBlank
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    @NotNull
    private Integer orderId;

    /**
     * 支払い金額.
     */
    @NotBlank
    private String payAmount;

    /**
     * 支払い方法.
     */
    @NotBlank
    private String paymentMethodCode;

    /**
     * 受付ID.
     */
    @NotBlank
    private String receivablesId;

    /**
     * 言語.
     */
    @NotBlank
    private String languages;
}
