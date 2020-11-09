package com.cnc.qr.api.csdvorder.resource;

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
     * 小計.
     */
    private BigDecimal orderAmount;

    /**
     * 外税金額.
     */
    private BigDecimal foreignTax;

    /**
     * 配達費.
     */
    private BigDecimal cateringCharge;

    /**
     * 合計.
     */
    private BigDecimal totalAmount;

    /**
     * 商品情報リスト.
     */
    private List<OrderItemsDto> itemList;

    /**
     * 出前区分.
     */
    private String deliveryTypeFlag;

    /**
     * 状態.
     */
    private String status;

    /**
     * 氏名.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String phoneNumber;

    /**
     * 出前時間.
     */
    private String deliveryTime;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 町域番地ID.
     */
    private String blockId;

    /**
     * 住所.
     */
    private String address;

    /**
     * メール.
     */
    private String email;

    /**
     * 要望.
     */
    private String comment;
}
