package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 店舗言語.
 */
@Data
@AllArgsConstructor
public class StoreLanguageDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表示顺.
     */
    private String sortNo;

    /**
     * 言語.
     */
    private String code;
}
