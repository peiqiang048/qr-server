package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 飲み放題削除情報取得条件.
 */
@Data
public class BuffetDelInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 放題ID.
     */
    private List<BuffetInputDto> buffetList;

}
