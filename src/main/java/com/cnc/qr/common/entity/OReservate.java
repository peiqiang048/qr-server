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
 * 予約テーブルエンティティ.
 */
@Entity
@Table(name = "o_reservate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OReservate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "reservate_id")
    private Integer reservateId;

    @Column(name = "reservate_time")
    private ZonedDateTime reservateTime;

    @Column(name = "use_time", precision = 2, scale = 1)
    private BigDecimal useTime;

    @Size(max = 100)
    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Size(max = 15)
    @Column(name = "tel_number", length = 15)
    private String telNumber;

    @Column(name = "customer_count")
    private Integer customerCount;

    @Size(max = 2)
    @Column(name = "reservate_type", length = 2)
    private String reservateType;

    @Size(max = 200)
    @Column(name = "comment", length = 200)
    private String comment;

    @Size(max = 2)
    @Column(name = "status", length = 2)
    private String status;

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

    public OReservate storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getReservateId() {
        return reservateId;
    }

    public OReservate reservateId(Integer reservateId) {
        this.reservateId = reservateId;
        return this;
    }

    public void setReservateId(Integer reservateId) {
        this.reservateId = reservateId;
    }

    public ZonedDateTime getReservateTime() {
        return reservateTime;
    }

    public OReservate reservateTime(ZonedDateTime reservateTime) {
        this.reservateTime = reservateTime;
        return this;
    }

    public void setReservateTime(ZonedDateTime reservateTime) {
        this.reservateTime = reservateTime;
    }

    public BigDecimal getUseTime() {
        return useTime;
    }

    public OReservate useTime(BigDecimal useTime) {
        this.useTime = useTime;
        return this;
    }

    public void setUseTime(BigDecimal useTime) {
        this.useTime = useTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OReservate customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public OReservate telNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public OReservate customerCount(Integer customerCount) {
        this.customerCount = customerCount;
        return this;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public String getReservateType() {
        return reservateType;
    }

    public OReservate reservateType(String reservateType) {
        this.reservateType = reservateType;
        return this;
    }

    public void setReservateType(String reservateType) {
        this.reservateType = reservateType;
    }

    public String getComment() {
        return comment;
    }

    public OReservate comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public OReservate status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getDelFlag() {
        return delFlag;
    }

    public OReservate delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OReservate insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OReservate insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OReservate updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OReservate updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OReservate version(Integer version) {
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
        if (!(o instanceof OReservate)) {
            return false;
        }
        return id != null && id.equals(((OReservate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OReservate{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", reservateId='" + getReservateId() + "'" +
            ", reservateTime=" + getReservateTime() +
            ", useTime=" + getUseTime() +
            ", customerName='" + getCustomerName() + "'" +
            ", telNumber='" + getTelNumber() + "'" +
            ", customerCount=" + getCustomerCount() +
            ", reservateType='" + getReservateType() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
