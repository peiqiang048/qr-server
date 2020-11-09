package com.cnc.qr.core.order.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出前QRコード印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePrintStatusDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    @NotBlank
    private String storeId;

    /**
     * 注文サマリID.
     */
    @NotBlank
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    @NotBlank
    private String orderId;

    /**
     * 印刷状態.
     */
    @NotBlank
    private String printStatus;
}
