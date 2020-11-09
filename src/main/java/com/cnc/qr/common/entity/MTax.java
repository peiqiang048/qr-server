package com.cnc.qr.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 税設定マスタエンティティ.
 */
@Entity
@Table(name = "m_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "business_id", length = 10)
    private String businessId;

    @Column(name = "tax_id")
    private Integer taxId;

    @Column(name = "tax_name")
    private String taxName;

    @Size(max = 1)
    @Column(name = "tax_code", length = 1)
    private String taxCode;

    @Size(max = 1)
    @Column(name = "tax_round_type", length = 1)
    private String taxRoundType;

    @Size(max = 1)
    @Column(name = "tax_relief_apply_type", length = 1)
    private String taxReliefApplyType;

    @Column(name = "tax_rate_normal", precision = 21, scale = 2)
    private BigDecimal taxRateNormal;

    @Column(name = "tax_rate_relief", precision = 21, scale = 2)
    private BigDecimal taxRateRelief;

    @Column(name = "apply_date")
    private ZonedDateTime applyDate;

    @Column(name = "sort_order")
    private Integer sortOrder;

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

    public MTax businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public MTax taxId(Integer taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public MTax taxName(String taxName) {
        this.taxName = taxName;
        return this;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public MTax taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxRoundType() {
        return taxRoundType;
    }

    public MTax taxRoundType(String taxRoundType) {
        this.taxRoundType = taxRoundType;
        return this;
    }

    public void setTaxRoundType(String taxRoundType) {
        this.taxRoundType = taxRoundType;
    }

    public String getTaxReliefApplyType() {
        return taxReliefApplyType;
    }

    public MTax taxReliefApplyType(String taxReliefApplyType) {
        this.taxReliefApplyType = taxReliefApplyType;
        return this;
    }

    public void setTaxReliefApplyType(String taxReliefApplyType) {
        this.taxReliefApplyType = taxReliefApplyType;
    }

    public BigDecimal getTaxRateNormal() {
        return taxRateNormal;
    }

    public MTax taxRateNormal(BigDecimal taxRateNormal) {
        this.taxRateNormal = taxRateNormal;
        return this;
    }

    public void setTaxRateNormal(BigDecimal taxRateNormal) {
        this.taxRateNormal = taxRateNormal;
    }

    public BigDecimal getTaxRateRelief() {
        return taxRateRelief;
    }

    public MTax taxRateRelief(BigDecimal taxRateRelief) {
        this.taxRateRelief = taxRateRelief;
        return this;
    }

    public void setTaxRateRelief(BigDecimal taxRateRelief) {
        this.taxRateRelief = taxRateRelief;
    }

    public ZonedDateTime getApplyDate() {
        return applyDate;
    }

    public MTax applyDate(ZonedDateTime applyDate) {
        this.applyDate = applyDate;
        return this;
    }

    public void setApplyDate(ZonedDateTime applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public MTax sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MTax delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MTax insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MTax insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MTax updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MTax updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MTax version(Integer version) {
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
        if (!(o instanceof MTax)) {
            return false;
        }
        return id != null && id.equals(((MTax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MTax{" +
            "id=" + getId() +
            ", businessId='" + getBusinessId() + "'" +
            ", taxId=" + getTaxId() +
            ", taxName='" + getTaxName() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", taxRoundType='" + getTaxRoundType() + "'" +
            ", taxReliefApplyType='" + getTaxReliefApplyType() + "'" +
            ", taxRateNormal=" + getTaxRateNormal() +
            ", taxRateRelief=" + getTaxRateRelief() +
            ", applyDate='" + getApplyDate() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
