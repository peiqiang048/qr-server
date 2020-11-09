package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * オプション情報取得条件.
 */
@Data
public class GetOptionInputDto implements Serializable {

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
     * 言語.
     */
    @NotBlank
    private String languages;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
}
