package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 役割情報取得結果.
 */
@Data
public class GetRoleOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 役割名.
     */
    private String roleName;

    /**
     * 選択した機能.
     */
    private List<ModelInfoDto> checkedModelList;

    /**
     * 機能リスト.
     */
    private List<ModelInfoDto> modelList;
}
