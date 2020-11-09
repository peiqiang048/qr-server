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
 * 支払会社情報テーブルエンティティ.
 */
@Entity
@Table(name = "p_payment_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PPaymentCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Size(max = 4)
    @Column(name = "company_id", length = 4)
    private String companyId;

    @Size(max = 4)
    @Column(name = "company_method", length = 4)
    private String companyMethod;

    @Size(max = 100)
    @Column(name = "company_name", length = 100)
    private String companyName;

    @Size(max = 50)
    @Column(name = "term_id", length = 50)
    private String termId;

    @Size(max = 50)
    @Column(name = "key", length = 50)
    private String key;

    @Size(max = 50)
    @Column(name = "sid", length = 50)
    private String sid;

    @Size(max = 50)
    @Column(name = "url", length = 50)
    private String url;

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

    @Size(max = 10)
    @Column(name = "store_code", length = 10)
    private String storeCode;

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

    public PPaymentCompany storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public PPaymentCompany companyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyMethod() {
        return companyMethod;
    }

    public PPaymentCompany companyMethod(String companyMethod) {
        this.companyMethod = companyMethod;
        return this;
    }

    public void setCompanyMethod(String companyMethod) {
        this.companyMethod = companyMethod;
    }

    public String getCompanyName() {
        return companyName;
    }

    public PPaymentCompany companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTermId() {
        return termId;
    }

    public PPaymentCompany termId(String termId) {
        this.termId = termId;
        return this;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getKey() {
        return key;
    }

    public PPaymentCompany key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSid() {
        return sid;
    }

    public PPaymentCompany sid(String sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return url;
    }

    public PPaymentCompany url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String payStatus) {
        this.url = url;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public PPaymentCompany delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public PPaymentCompany insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public PPaymentCompany insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public PPaymentCompany updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public PPaymentCompany updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public PPaymentCompany version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public PPaymentCompany storeCode(String storeCode) {
        this.storeCode = storeCode;
        return this;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PPaymentCompany)) {
            return false;
        }
        return id != null && id.equals(((PPaymentCompany) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PTrioCompany{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyMethod=" + getCompanyMethod() +
            ", companyName='" + getCompanyName() + "'" +
            ", termId='" + getTermId() + "'" +
            ", key=" + getKey() +
            ", sid='" + getSid() + "'" +
            ", url='" + getUrl() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() + "'" +
            ", storeCode=" + getStoreCode() +
            "}";
    }
}
