package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.AmountSoldDto;
import com.cnc.qr.core.order.model.GetCallDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class AmountSoldOutputResource extends CommonOutputResource {


    /**
     * 売上総件数.
     */
    private Long soldOrderCount;

    /**
     * 注文総額.
     */
    private String totalOrderAmount;

    /**
     * 割引総額.
     */
    private String totalDiscount;

    /**
     * 支払総額.
     */
    private BigDecimal totalPay;


    /**
     * 売上リスト.
     */
    private List<AmountSoldDto> amountSold;
}
