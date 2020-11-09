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
public class AmountSoldInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 開始時間.
     */
    private String startDate;

    /**
     * 終了時間.
     */
    private String endDate;

    /**
     * 支払方式.
     */
    private String paymentMethod;

    /**
     * ページ.
     */
    private int page = 0;

    /**
     * ページサイズ.
     */
    private int pageCount = 5;
}
