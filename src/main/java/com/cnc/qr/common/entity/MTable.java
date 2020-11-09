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
 * テーブルマスタエンティティ.
 */
@Entity
@Table(name = "m_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "table_id")
    private Integer tableId;

    @Size(max = 100)
    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "table_seat_count")
    private Integer tableSeatCount;


    @Column(name = "table_index_id")
    private Integer tableIndexId;

    @Size(max = 2)
    @Column(name = "table_type", length = 2)
    private String tableType;

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

    public MTable storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public MTable tableId(Integer tableId) {
        this.tableId = tableId;
        return this;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public MTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTableSeatCount() {
        return tableSeatCount;
    }

    public MTable tableSeatCount(Integer tableSeatCount) {
        this.tableSeatCount = tableSeatCount;
        return this;
    }

    public void setTableSeatCount(Integer tableSeatCount) {
        this.tableSeatCount = tableSeatCount;
    }

    public Integer getTableIndexId() {
        return tableIndexId;
    }

    public MTable tableIndexId(Integer tableIndexId) {
        this.tableIndexId = tableIndexId;
        return this;
    }

    public void setTableIndexId(Integer tableIndexId) {
        this.tableIndexId = tableIndexId;
    }

    public String getTableType() {
        return tableType;
    }

    public MTable tableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MTable delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MTable insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MTable insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MTable updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MTable updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MTable version(Integer version) {
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
        if (!(o instanceof MTable)) {
            return false;
        }
        return id != null && id.equals(((MTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MTable{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", tableId=" + getTableId() +
            ", tableName='" + getTableName() + "'" +
            ", tableSeatCount=" + getTableSeatCount() +
            ", tableIndexId=" + getTableIndexId() +
            ", tableType='" + getTableType() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
