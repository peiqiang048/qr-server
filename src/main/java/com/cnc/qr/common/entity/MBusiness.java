package com.cnc.qr.common.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ビジネスマスタエンティティ.
 */
@Entity
@Table(name = "m_business")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 10)
    @Column(name = "business_id", length = 10)
    private String businessId;

    @Size(max = 200)
    @Column(name = "business_name", length = 200)
    private String businessName;

    @Size(max = 200)
    @Column(name = "owner_name", length = 200)
    private String ownerName;

    @Size(max = 100)
    @Column(name = "owner_name_ktkn", length = 100)
    private String ownerNameKtkn;

    @Size(max = 400)
    @Column(name = "address", length = 400)
    private String address;

    @Size(max = 10)
    @Column(name = "post_number", length = 10)
    private String postNumber;

    @Size(max = 15)
    @Column(name = "tel_number", length = 15)
    private String telNumber;

    @Size(max = 15)
    @Column(name = "fax_number", length = 15)
    private String faxNumber;

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

    public MBusiness businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public MBusiness businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public MBusiness ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerNameKtkn() {
        return ownerNameKtkn;
    }

    public MBusiness ownerNameKtkn(String ownerNameKtkn) {
        this.ownerNameKtkn = ownerNameKtkn;
        return this;
    }

    public void setOwnerNameKtkn(String ownerNameKtkn) {
        this.ownerNameKtkn = ownerNameKtkn;
    }

    public String getAddress() {
        return address;
    }

    public MBusiness address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public MBusiness postNumber(String postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public MBusiness telNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public MBusiness faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MBusiness delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MBusiness insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MBusiness insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MBusiness updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MBusiness updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MBusiness version(Integer version) {
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
        if (!(o instanceof MBusiness)) {
            return false;
        }
        return id != null && id.equals(((MBusiness) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBusiness{" +
            "id=" + getId() +
            ", businessId='" + getBusinessId() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", ownerNameKtkn='" + getOwnerNameKtkn() + "'" +
            ", address='" + getAddress() + "'" +
            ", postNumber='" + getPostNumber() + "'" +
            ", telNumber='" + getTelNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
