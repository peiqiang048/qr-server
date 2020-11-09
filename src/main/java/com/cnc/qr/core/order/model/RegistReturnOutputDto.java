package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 返品.
 */
@Data
public class RegistReturnOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文詳細ID.
     */
    private Integer orderDetailId;


}
