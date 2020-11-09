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
 * 店舗媒体マスタエンティティ.
 */
@Entity
@Table(name = "m_store_medium")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MStoreMedium implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "medium_id")
    private Integer mediumId;

    @Size(max = 3)
    @Column(name = "medium_type", length = 3)
    private String mediumType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Size(max = 2)
    @Column(name = "terminal_distinction", length = 2)
    private String terminalDistinction;

    @Size(max = 400)
    @Column(name = "image_path", length = 400)
    private String imagePath;

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

    public String getStoreId() {
        return storeId;
    }

    public MStoreMedium storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getMediumId() {
        return mediumId;
    }

    public MStoreMedium mediumId(Integer mediumId) {
        this.mediumId = mediumId;
        return this;
    }

    public void setMediumId(Integer mediumId) {
        this.mediumId = mediumId;
    }

    public String getMediumType() {
        return mediumType;
    }

    public MStoreMedium mediumType(String mediumType) {
        this.mediumType = mediumType;
        return this;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public MStoreMedium sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTerminalDistinction() {
        return terminalDistinction;
    }

    public MStoreMedium terminalDistinction(String terminalDistinction) {
        this.terminalDistinction = terminalDistinction;
        return this;
    }

    public void setTerminalDistinction(String terminalDistinction) {
        this.terminalDistinction = terminalDistinction;
    }

    public String getImagePath() {
        return imagePath;
    }

    public MStoreMedium imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MStoreMedium delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MStoreMedium insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MStoreMedium insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MStoreMedium updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MStoreMedium updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MStoreMedium version(Integer version) {
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
        if (!(o instanceof MStoreMedium)) {
            return false;
        }
        return id != null && id.equals(((MStoreMedium) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MStoreMedium{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", mediumId=" + getMediumId() +
            ", mediumType='" + getMediumType() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", terminalDistinction='" + getTerminalDistinction() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
