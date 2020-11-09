package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 役割情報編集データ.
 */
@Data
public class RegisterOptionTypeInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * オプション種類ID.
     */
    private String optionTypeCd;

    /**
     * オプション種類名.
     */
    private String optionTypeName;

    /**
     * 区分.
     */
    private String classification;
}
