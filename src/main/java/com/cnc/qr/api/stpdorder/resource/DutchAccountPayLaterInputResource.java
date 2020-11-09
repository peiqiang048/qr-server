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
public class DutchAccountPayLaterInputResource {

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
     * 外税金額.
     */
    @NotBlank
    private String sotoTax;

    /**
     * 就餐区分.
     */
    @NotBlank
    private String takeoutFlag;

    /**
     * 小計.
     */
    @NotBlank
    private String subtotal;

    /**
     * 二维码コード.
     */
    @NotBlank
    private String payCodeId;

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
     * 语言.
     */
    @NotBlank
    private String languages;
}
