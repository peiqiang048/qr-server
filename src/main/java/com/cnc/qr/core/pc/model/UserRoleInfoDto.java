package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * ユーザ役割情報.
 */
@Data
public class UserRoleInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * No.
     */
    private Integer num;

    /**
     * 役割ID.
     */
    private String roleId;

    /**
     * 役割名.
     */
    private String roleName;

    /**
     * 停用标识.
     */
    private String status;

    /**
     * コンストラクタ.
     *
     * @param roleId   役割ID
     * @param roleName 役割名
     * @param status   停用标识
     */
    public UserRoleInfoDto(Integer num, Integer roleId, String roleName, String status) {
        this.num = num;
        this.roleId = roleId.toString();
        this.roleName = roleName;
        this.status = status;
    }
}
