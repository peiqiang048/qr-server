package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザ情報編集データ.
 */
@Data
public class RegisterUserInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * ビジネスID.
     */
    private String businessId;


    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザID.
     */
    private Integer userId;

    /**
     * ログインID.
     */
    private String loginId;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * 区分.
     */
    private String classification;

    /**
     * 選択した役割.
     */
    private List<RegisterRoleInfoDto> checkedRoleList;
}
