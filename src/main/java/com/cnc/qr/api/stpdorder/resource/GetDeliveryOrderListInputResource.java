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
public class GetDeliveryOrderListInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 開始日付.
     */
    private String deliveryOrderTimeFrom;

    /**
     * 終了日付.
     */
    private String deliveryOrderTimeTo;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 状態.
     */
    private String status;

    /**
     * ページ.
     */
    private int page = 0;

    /**
     * ページサイズ.
     */
    private int pageCount = 5;
}
