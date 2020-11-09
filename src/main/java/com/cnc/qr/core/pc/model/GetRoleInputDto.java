package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 役割情報取得条件.
 */
@Data
public class GetRoleInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 役割ID.
     */
    private Integer roleId;

    /**
     * 言語.
     */
    private String languages;
}
