package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 飲食区分取得検索結果.
 */
@Data
public class TrioPayLaterOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 支払結果.
     */
    private String respCode;
    
    /**
     * 取消結果.
     */
    private String cancelRespCode;
}
