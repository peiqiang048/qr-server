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
public class GetOrderListInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付No.
     */
    private Integer receivablesNo;

    /**
     * 開始日付.
     */
    private String orderDateStart;

    /**
     * 終了日付.
     */
    private String orderDateEnd;

    /**
     * 注文状態.
     */
    private String orderStatus;

    /**
     * 飲食区分.
     */
    private String takeoutFlag;

    /**
     * ページ.
     */
    private int page = 0;

    /**
     * ページサイズ.
     */
    private int pageCount = 5;
}
