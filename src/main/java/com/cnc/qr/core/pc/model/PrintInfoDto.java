package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * プリンタ情報取得検索結果.
 */
@Data
public class PrintInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンタID.
     */
    private String printId;

    /**
     * プリンタ名.
     */
    private String printName;
}
