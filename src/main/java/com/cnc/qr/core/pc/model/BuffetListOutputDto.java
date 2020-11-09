package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * のみ放題情報取得検索結果.
 */
@Data
public class BuffetListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * のみ放題情報.
     */
    private List<BuffetDto> buffetList;

    /**
     * 总条数.
     */
    private Long totalCount;
}
