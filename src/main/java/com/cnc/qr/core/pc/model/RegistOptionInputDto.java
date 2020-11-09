package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品情報取得条件.
 */
@Data
public class RegistOptionInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * オプションコード.
     */
    private String optionCode;
    
    /**
     * オプション名.
     */
    private String optionName;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
}
