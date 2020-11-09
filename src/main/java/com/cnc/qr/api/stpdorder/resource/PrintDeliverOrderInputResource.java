package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import java.math.BigDecimal;
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
public class PrintDeliverOrderInputResource {

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
     * レシート種類フラグ（１：キッチン、２：レシート、３：領収書）.
     */
    @NotBlank
    private String deliverPrintType;

    /**
     * 支払金額.
     */
    private BigDecimal paymentAmount;
}
