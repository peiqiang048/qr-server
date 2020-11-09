package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * プリンター編集情報取得条件.
 */
@Data
public class GetPrintInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * プリンターID.
     */
    private String printId;

}
