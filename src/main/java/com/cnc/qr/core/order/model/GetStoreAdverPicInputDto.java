package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 店舗媒体情報取得条件.
 */
@Data
public class GetStoreAdverPicInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 媒体种类.
     */
    private String mediumType;

    /**
     * 用途区分.
     */
    private String terminalDistinction;

}
