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
 * オプション種類マスタエンティティ.
 */
@Entity
@Table(name = "m_option_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MOptionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Size(max = 4)
    @Column(name = "option_type_cd", length = 4)
    private String optionTypeCd;

    @Column(name = "option_type_name")
    private String optionTypeName;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Size(max = 2)
    @Column(name = "classification", length = 2)
    private String classification;

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

    public MOptionType storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOptionTypeCd() {
        return optionTypeCd;
    }

    public MOptionType optionTypeCd(String optionTypeCd) {
        this.optionTypeCd = optionTypeCd;
        return this;
    }

    public void setOptionTypeCd(String optionTypeCd) {
        this.optionTypeCd = optionTypeCd;
    }

    public String getOptionTypeName() {
        return optionTypeName;
    }

    public MOptionType optionTypeName(String optionTypeName) {
        this.optionTypeName = optionTypeName;
        return this;
    }

    public void setOptionTypeName(String optionTypeName) {
        this.optionTypeName = optionTypeName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public MOptionType sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getClassification() {
        return classification;
    }

    public MOptionType classification(String classification) {
        this.classification = classification;
        return this;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MOptionType delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MOptionType insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MOptionType insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MOptionType updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MOptionType updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MOptionType version(Integer version) {
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
        if (!(o instanceof MOptionType)) {
            return false;
        }
        return id != null && id.equals(((MOptionType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MOptionType{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", optionTypeCd='" + getOptionTypeCd() + "'" +
            ", optionTypeName='" + getOptionTypeName() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", classification='" + getClassification() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
