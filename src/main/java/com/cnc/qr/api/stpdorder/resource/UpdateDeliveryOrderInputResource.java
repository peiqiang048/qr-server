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
public class UpdateDeliveryOrderInputResource {

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
     * 氏名.
     */
    @NotBlank
    private String customerName;

    /**
     * 電話番号.
     */
    @NotBlank
    private String telNumber;

    /**
     * 出前時間.
     */
    @NotBlank
    private String deliveryTime;

    /**
     * メール.
     */
    private String mailAddress;

    /**
     * 状態.
     */
    @NotBlank
    private String status;

    /**
     * 出前仕方.
     */
    @NotBlank
    private String deliveryTypeFlag;

    /**
     * 要望.
     */
    private String comment;

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
    private String other;

}
