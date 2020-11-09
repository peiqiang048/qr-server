package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブル情報編集データ.
 */
@Data
public class RegisterAreaInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * エリアID.
     */
    private Integer areaId;

    /**
     * エリア名.
     */
    private String areaName;


}
