package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 店舗言語.
 */
@Data
@AllArgsConstructor
public class LanguageInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 言語.
     */
    private String code;

    /**
     * 言語名.
     */
    private String name;
}
