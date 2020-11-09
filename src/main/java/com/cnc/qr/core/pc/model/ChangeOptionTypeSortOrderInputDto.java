package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザステータス更新データ.
 */
@Data
public class ChangeOptionTypeSortOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * オプション種類順番リスト.
     */
    private List<OptionTypeCdListDto> optionTypeSortOrderList;
}
