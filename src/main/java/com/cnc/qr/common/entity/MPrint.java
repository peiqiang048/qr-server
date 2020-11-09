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
 * プリンタマスタエンティティ.
 */
@Entity
@Table(name = "m_print")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "print_id")
    private Integer printId;

    @Size(max = 100)
    @Column(name = "print_name", length = 100)
    private String printName;

    @Size(max = 15)
    @Column(name = "print_ip", length = 15)
    private String printIp;

    @Size(max = 100)
    @Column(name = "bluetooth_name", length = 100)
    private String bluetoothName;

    @Size(max = 2)
    @Column(name = "brand_code", length = 2)
    private String brandCode;

    @Size(max = 100)
    @Column(name = "print_model_no", length = 100)
    private String printModel;

    @Size(max = 2)
    @Column(name = "connection_method_code", length = 2)
    private String connectionMethodCode;

    @Size(max = 2)
    @Column(name = "print_size", length = 2)
    private String printSize;

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

    public MPrint storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getPrintId() {
        return printId;
    }

    public MPrint printId(Integer printId) {
        this.printId = printId;
        return this;
    }

    public void setPrintId(Integer printId) {
        this.printId = printId;
    }

    public String getPrintName() {
        return printName;
    }

    public MPrint printName(String printName) {
        this.printName = printName;
        return this;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getPrintIp() {
        return printIp;
    }

    public MPrint printIp(String printIp) {
        this.printIp = printIp;
        return this;
    }

    public void setPrintIp(String printIp) {
        this.printIp = printIp;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public MPrint bluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
        return this;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public MPrint brandCode(String brandCode) {
        this.brandCode = brandCode;
        return this;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getPrintModel() {
        return printModel;
    }

    public MPrint printModel(String printModel) {
        this.printModel = printModel;
        return this;
    }

    public void setPrintModel(String printModel) {
        this.printModel = printModel;
    }

    public String getConnectionMethodCode() {
        return connectionMethodCode;
    }

    public MPrint connectionMethodCode(String connectionMethodCode) {
        this.connectionMethodCode = connectionMethodCode;
        return this;
    }

    public void setConnectionMethodCode(String connectionMethodCode) {
        this.connectionMethodCode = connectionMethodCode;
    }

    public String getPrintSize() {
        return printSize;
    }

    public MPrint printSize(String printSize) {
        this.printSize = printSize;
        return this;
    }

    public void setPrintSize(String printSize) {
        this.printSize = printSize;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MPrint delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MPrint insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MPrint insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MPrint updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MPrint updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MPrint version(Integer version) {
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
        if (!(o instanceof MPrint)) {
            return false;
        }
        return id != null && id.equals(((MPrint) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrint{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", printId=" + getPrintId() +
            ", printName='" + getPrintName() + "'" +
            ", printIp='" + getPrintIp() + "'" +
            ", bluetoothName='" + getBluetoothName() + "'" +
            ", brandCode='" + getBrandCode() + "'" +
            ", printModel='" + getPrintModel() + "'" +
            ", connectionMethodCode='" + getConnectionMethodCode() + "'" +
            ", printSize='" + getPrintSize() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
