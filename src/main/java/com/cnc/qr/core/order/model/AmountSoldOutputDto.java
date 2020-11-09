package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 売上リスト.
 */
@Data
public class AmountSoldOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 売上総件数.
     */
    private Long soldOrderCount;


    /**
     * 支払総額.
     */
    private BigDecimal totalPay;


    /**
     * 売上リスト.
     */
    private Page<AmountSoldDto> amountSold;
}
