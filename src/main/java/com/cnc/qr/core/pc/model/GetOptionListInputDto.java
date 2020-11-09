package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * オプション一覧情報取得条件.
 */
@Data
public class GetOptionListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * オプション種類コード.
     */
    private String optionTypeCd;

    /**
     * オプション名称.
     */
    private String optionName;
    
    /**
     * 言語.
     */
    private String languages;
}
