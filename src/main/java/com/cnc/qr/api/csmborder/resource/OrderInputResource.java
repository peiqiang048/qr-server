package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.OrderItemsDto;
import java.math.BigDecimal;
import java.util.List;
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
public class OrderInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 受付ID.
     */

    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String receivablesId;

    /**
     * 桌号.
     */
    private String tableId;


    /**
     * 就餐区分.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.NUMERIC_VALIDATION)
    private String takeoutFlag;


    /**
     * 注文金额.
     */
    private BigDecimal orderAmount;

    /**
     * 版本.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.NUMERIC_VALIDATION)
    private String version;


    /**
     * 商品情報リスト.
     */
    private List<OrderItemsDto> items;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 支払区分.
     */
    private String paymentType;

    /**
     * 放题ID.
     */
    private Integer buffetId;

    /**
     * 要望
     */
    private String comment;

}
