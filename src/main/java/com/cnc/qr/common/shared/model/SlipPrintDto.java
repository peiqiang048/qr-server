package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlipPrintDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * QR情報.
     */
    private QrCodeOutputDto qrCodeDto;

    /**
     * 厨房用情報.
     */
    private KitchenOutputDto kitchenDto;

    /**
     * 客用伝票.
     */
    private CustomerOrderInfoDto customerOrderInfoDto;

    /**
     * 会計伝票.
     */
    private OrderAccountInfoDto orderAccountInfoDto;

    /**
     * 領収書伝票.
     */
    private ReceiptInfoDto receiptInfoDto;

    /**
     * 点検精算伝票.
     */
    private InspectionSettleInfoDto inspectionSettleInfoDto;

    /**
     * 注文サマリID(轮询时用的).
     */
    private String  orderSummaryId;
    /**
     * 注文ID(轮询时用的).
     */
    private String orderId;
}
