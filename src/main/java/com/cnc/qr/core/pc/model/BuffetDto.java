package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * のみ放題情報取得検索結果.
 */
@Data
public class BuffetDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * 放題ID.
     */
    private String buffetId;

    /**
     * 放題名.
     */
    private String buffetName;

    /**
     * 单价.
     */
    private String buffetPrice;

    /**
     * 状态.
     */
    private String buffetStatus;

}
