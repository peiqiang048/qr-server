package com.cnc.qr.api.stmborder.resource;

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
public class RegisterOrderInputResource {

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
    private String receivablesId;

    /**
     * 桌号.
     */
    private String tableId;

    /**
     * 注文金额.
     */
    private BigDecimal orderAmount;

    /**
     * 商品情報リスト.
     */
    private List<OrderItemsDto> items;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 要望.
     */
    private String comment;

}
