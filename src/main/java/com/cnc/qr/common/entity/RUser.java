package com.cnc.qr.common.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ユーザテーブルエンティティ.
 */
@Entity
@Table(name = "r_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "business_id", length = 10)
    private String businessId;

    @Column(name = "user_id")
    private Integer userId;

    @Size(max = 20)
    @Column(name = "login_id", length = 20)
    private String loginId;

    @Size(max = 64)
    @Column(name = "user_password", length = 64)
    private String userPassword;

    @Size(max = 64)
    @Column(name = "user_name", length = 64)
    private String userName;

    @Column(name = "user_last_login_time")
    private ZonedDateTime userLastLoginTime;

    @Column(name = "user_login_count")
    private Integer userLoginCount;

    @Column(name = "stop_flag")
    private Integer stopFlag;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Size(max = 100)
    @Column(name = "ins_oper_cd", length = 100)
    private String insOperCd;

    @Column(name = "ins_date_time")
    private ZonedDateTime insDateTime;

    @Size(max = 100)
    @Column(name = "upd_oper_cd", length = 100)
    private String updOperCd;

    @Column(name = "upd_date_time")
    private ZonedDateTime updDateTime;

    @Column(name = "version")
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public RUser businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getUserId() {
        return userId;
    }

    public RUser userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public RUser loginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public RUser userPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public RUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ZonedDateTime getUserLastLoginTime() {
        return userLastLoginTime;
    }

    public RUser userLastLoginTime(ZonedDateTime userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
        return this;
    }

    public void setUserLastLoginTime(ZonedDateTime userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    public RUser userLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
        return this;
    }

    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }


    public Integer getStopFlag() {
        return stopFlag;
    }

    public RUser stopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
        return this;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public RUser delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public RUser insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public RUser insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public RUser updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public RUser updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public RUser version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RUser)) {
            return false;
        }
        return id != null && id.equals(((RUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RUser{" +
            "id=" + getId() +
            ", businessId='" + getBusinessId() + "'" +
            ", userId=" + getUserId() +
            ", loginId='" + getLoginId() + "'" +
            ", userPassword='" + getUserPassword() + "'" +
            ", userName='" + getUserName() + "'" +
            ", userLastLoginTime='" + getUserLastLoginTime() + "'" +
            ", userLoginCount=" + getUserLoginCount() +
            ", stopFlag=" + getStopFlag() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
