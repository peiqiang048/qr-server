package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 役割ステータス更新データ.
 */
@Data
public class ChangeRoleStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 役割IDリスト.
     */
    private List<RoleIdDto> roleList;

    /**
     * 区分.
     */
    private Integer classification;
}
