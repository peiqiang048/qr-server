package com.cnc.qr.core.pc.model;

import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.util.DateUtil;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;

/**
 * ユーザ情報.
 */
@Data
public class UserInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No.
     */
    private Integer num;

    /**
     * ユーザID.
     */
    private String userId;

    /**
     * ログインID.
     */
    private String loginId;

    /**
     * ユーザ名.
     */
    private String userName;
    /**
     * バージョン.
     */
    private Integer version;
    /**
     * 上次登录时间.
     */
    private String lastLoginTime;

    /**
     * ユーザ区分.
     */
    private String userClassification;

    /**
     * 停用标识.
     */
    private String stopFlag;

    /**
     * コンストラクタ.
     *
     * @param loginId            ログインID
     * @param userName           ユーザ名
     * @param lastLoginTime      上次登录时间
     * @param userClassification ユーザ区分
     */
    public UserInfoDto(Integer num, Integer userId, String loginId, String userName,
        Integer version, ZonedDateTime lastLoginTime,
        String userClassification, String stopFlag) {
        this.num = num;
        this.userId = userId.toString();
        this.loginId = loginId;
        this.userName = userName;
        this.version = version;
        this.lastLoginTime = DateUtil
            .getZonedDateString(lastLoginTime, CommonConstants.DATE_FORMAT_DATETIME);
        this.userClassification = userClassification;
        this.stopFlag = stopFlag.toString();
    }
}
