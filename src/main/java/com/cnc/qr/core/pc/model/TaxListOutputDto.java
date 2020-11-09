package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 税設定情報取得検索結果.
 */
@Data
public class TaxListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 税設定情報.
     */
    private List<TaxDto> taxList;
}
