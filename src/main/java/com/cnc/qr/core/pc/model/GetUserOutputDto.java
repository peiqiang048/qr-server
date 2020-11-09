package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザ情報取得結果.
 */
@Data
public class GetUserOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * ログインID.
     */
    private String loginId;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * ユーザ区分.
     */
    private String classification;

    /**
     * 選択した役割.
     */
    private List<RoleInfoDto> checkedRoleList;

    /**
     * 区分リスト.
     */
    private List<ClassificationInfoDto> classificationList;

    /**
     * 役割リスト.
     */
    private List<RoleInfoDto> roleList;
}
