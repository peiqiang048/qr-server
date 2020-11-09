package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * のみ放題情報取得条件.
 */
@Data
public class BuffetListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 放題名.
     */
    private String buffetName;

    /**
     * 状态.
     */
    private String buffetStatus;

    /**
     * 当前语言.
     */
    private String languages;

    /**
     * カテゴリーID.
     */
    private Integer categoryId;

}
