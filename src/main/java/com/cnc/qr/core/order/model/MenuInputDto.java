package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * メニューインプット.
 */
@Data
public class MenuInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;

    /**
     * ユーザ.
     */
    private String userId;
}
