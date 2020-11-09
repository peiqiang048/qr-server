package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * オプションコード更新データ.
 */
@Data
public class ChangeOptionSortOrderInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * オプション種類コード.
     */
    private String optionTypeCd;
    
    /**
     * オプション順番リスト.
     */
    private List<OptionCodeDto> optionSortOrderList;
}
