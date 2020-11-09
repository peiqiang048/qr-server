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
 * 呼出テーブルエンティティ.
 */
@Entity
@Table(name = "o_call")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OCall implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "call_id")
    private Integer callId;

    @Size(max = 20)
    @Column(name = "receivables_id", length = 20)
    private String receivablesId;

    @Column(name = "call_number")
    private Integer callNumber;

    @Size(max = 2)
    @Column(name = "call_status", length = 2)
    private String callStatus;

    @Column(name = "table_id")
    private Integer tableId;

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

    public OCall storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getCallId() {
        return callId;
    }

    public OCall callId(Integer callId) {
        this.callId = callId;
        return this;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    public String getReceivablesId() {
        return receivablesId;
    }

    public OCall receivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
        return this;
    }

    public void setReceivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
    }

    public Integer getCallNumber() {
        return callNumber;
    }

    public OCall callNumber(Integer callNumber) {
        this.callNumber = callNumber;
        return this;
    }

    public void setCallNumber(Integer callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public OCall callStatus(String callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public Integer getTableId() {
        return tableId;
    }

    public OCall tableId(Integer tableId) {
        this.tableId = tableId;
        return this;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OCall delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OCall insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OCall insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OCall updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OCall updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OCall version(Integer version) {
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
        if (!(o instanceof OCall)) {
            return false;
        }
        return id != null && id.equals(((OCall) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OCall{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", callId=" + getCallId() +
            ", receivablesId='" + getReceivablesId() + "'" +
            ", callNumber=" + getCallNumber() +
            ", callStatus='" + getCallStatus() + "'" +
            ", tableId=" + getTableId() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
