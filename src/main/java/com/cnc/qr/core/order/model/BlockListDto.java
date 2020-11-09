package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 町域番地情報.
 */
@Data
public class BlockListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 町域番地ID.
     */
    private String blockId;

    /**
     * 町域番地名称.
     */
    private String blockName;
}
