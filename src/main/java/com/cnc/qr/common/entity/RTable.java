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
 * 商品マスタエンティティ.
 */
@Entity
@Table(name = "r_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "table_id")
    private Integer tableId;

    @Size(max = 20)
    @Column(name = "receivables_id", length = 20)
    private String receivablesId;

    @Size(max = 20)
    @Column(name = "real_receivables_id", length = 20)
    private String realReceivablesId;

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

    public RTable storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public RTable groupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public RTable tableId(Integer tableId) {
        this.tableId = tableId;
        return this;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getReceivablesId() {
        return receivablesId;
    }

    public RTable receivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
        return this;
    }

    public void setReceivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
    }

    public String getRealReceivablesId() {
        return realReceivablesId;
    }

    public RTable realReceivablesId(String realReceivablesId) {
        this.realReceivablesId = realReceivablesId;
        return this;
    }

    public void setRealReceivablesId(String realReceivablesId) {
        this.realReceivablesId = realReceivablesId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public RTable delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public RTable insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public RTable insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public RTable updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public RTable updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public RTable version(Integer version) {
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
        if (!(o instanceof RTable)) {
            return false;
        }
        return id != null && id.equals(((RTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RItem{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", groupId='" + getGroupId() + "'" +
            ", tableId='" + getTableId() + "'" +
            ", receivablesId='" + getReceivablesId() + "'" +
            ", realReceivablesId='" + getRealReceivablesId() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
