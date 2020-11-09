package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文確定情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetTableInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 桌号.
     */
    private List<TableIdDto> tableId;

}
