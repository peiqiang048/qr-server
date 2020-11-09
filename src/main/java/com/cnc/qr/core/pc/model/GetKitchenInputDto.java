package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * キッチン編集情報取得条件.
 */
@Data
public class GetKitchenInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * キッチンID.
     */
    private Integer kitchenId;

}
