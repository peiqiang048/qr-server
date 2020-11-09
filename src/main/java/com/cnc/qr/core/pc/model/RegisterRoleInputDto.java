package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 役割情報編集データ.
 */
@Data
public class RegisterRoleInputDto implements Serializable {

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
     * 役割名.
     */
    private String roleName;

    /**
     * 区分.
     */
    private String classification;

    /**
     * 選択した機能.
     */
    private List<ModelIdDto> checkedModelList;
}
