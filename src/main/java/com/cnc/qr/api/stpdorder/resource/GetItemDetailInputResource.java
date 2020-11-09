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
public class GetItemDetailInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 商品ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.NUMERIC_VALIDATION)
    private String itemId;

    /**
     * 放題フラグ.
     */
    private String buffetItemFlag;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 注文ID.
     */
    private String orderId;

    /**
     * 注文明細ID.
     */
    private String orderDetailId;

    /**
     * 言語.
     */
    @NotBlank
    private String languages;

}
