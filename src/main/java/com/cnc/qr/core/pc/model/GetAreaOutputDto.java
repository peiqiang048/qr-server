package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * エリア情報取得結果.
 */
@Data
public class GetAreaOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * エリア名.
     */
    private String areaName;
}
