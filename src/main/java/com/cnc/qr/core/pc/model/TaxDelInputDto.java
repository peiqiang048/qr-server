package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 税設定削除情報取得条件.
 */
@Data
public class TaxDelInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * 税IDリスト.
     */
    private List<TaxInputDto> taxIdList;

}
