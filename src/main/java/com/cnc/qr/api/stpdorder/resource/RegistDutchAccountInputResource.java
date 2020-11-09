package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.acct.model.ItemListDto;
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
public class RegistDutchAccountInputResource {

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
     * テーブルID.
     */
    @NotBlank
    private String tableId;

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
    @NotBlank
    private String sotoTax;

    /**
     * 小計.
     */
    @NotBlank
    private String subtotal;

    /**
     * 支払い方式コード.
     */
    @NotBlank
    private String paymentMethodCode;

    /**
     * お預かり金額.
     */
    @NotBlank
    private String custody;

    /**
     * 人数.
     */
    @NotBlank
    private String customerCount;

    /**
     * 最後注文フラグ.
     */
    @NotBlank
    private String lastOrderFlag;

    /**
     * 商品リスト.
     */
    @NotNull
    private List<@Valid ItemListDto> itemList;

    /**
     * 言語.
     */
    @NotBlank
    private String  languages;
}
