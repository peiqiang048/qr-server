package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗情報取得検索結果.
 */
@Data
public class KitchenDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * キチンID.
     */
    private String kitchenId;

    /**
     * キチン名.
     */
    private String kitchenName;

    /**
     * プリンター名.
     */
    private String printName;

    /**
     * ブランド.
     */
    private String brand;

    /**
     * 型番.
     */
    private String printModelNo;
}
