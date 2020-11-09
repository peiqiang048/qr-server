package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * オプション順番情報取得条件.
 */
@Data
public class GetOptionSortOrderListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * 言語.
     */
    private String languages;
    
    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
}
