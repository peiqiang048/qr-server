package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * キッチン保存インプット.
 */
@Data
public class DeleteKitchenInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * キッチン.
     */
    private List<KitchenInfoDto> kitchenList;

}
