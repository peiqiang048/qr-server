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
 * 店舗マスタエンティティ.
 */
@Entity
@Table(name = "m_store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Size(max = 100)
    @Column(name = "store_name", length = 100)
    private String storeName;

    @Size(max = 100)
    @Column(name = "store_name_ktkn", length = 100)
    private String storeNameKtkn;

    @Size(max = 400)
    @Column(name = "store_if", length = 400)
    private String storeIf;

    @Size(max = 400)
    @Column(name = "store_address", length = 400)
    private String storeAddress;

    @Size(max = 15)
    @Column(name = "store_phone", length = 15)
    private String storePhone;

    @Size(max = 10)
    @Column(name = "post_number", length = 10)
    private String postNumber;

    @Size(max = 15)
    @Column(name = "fax_number", length = 15)
    private String faxNumber;

    @Size(max = 5)
    @Column(name = "start_time", length = 5)
    private String startTime;

    @Size(max = 5)
    @Column(name = "end_time", length = 5)
    private String endTime;

    @Size(max = 1)
    @Column(name = "staff_check", length = 1)
    private String staffCheck;
    
    @Size(max = 1)
    @Column(name = "course_buffet_check", length = 1)
    private String courseBuffetCheck;

    @Size(max = 10)
    @Column(name = "business_id", length = 10)
    private String businessId;

    @Size(max = 64)
    @Column(name = "store_password", length = 64)
    private String storePassword;

    @Column(name = "default_use_time")
    private Integer defaultUseTime;

    @Size(max = 1)
    @Column(name = "customer_select_flag", length = 1)
    private String customerSelectFlag;

    @Column(name = "catering_interval_time")
    private Integer cateringIntervalTime;

    @Column(name = "takeout_interval_time")
    private Integer takeoutIntervalTime;

    @Column(name = "interval_time")
    private Integer intervalTime;

    @Size(max = 1)
    @Column(name = "delivery_flag", length = 1)
    private String deliveryFlag;

    @Size(max = 1)
    @Column(name = "delivery_type_flag", length = 1)
    private String deliveryTypeFlag;

    @Size(max = 1)
    @Column(name = "catering_charge_type", length = 1)
    private String cateringChargeType;

    @Column(name = "catering_charge_amount", precision = 7, scale = 0)
    private BigDecimal cateringChargeAmount;

    @Column(name = "catering_charge_percent", precision = 5, scale = 2)
    private BigDecimal cateringChargePercent;

    @Size(max = 5)
    @Column(name = "order_end_time", length = 5)
    private String orderEndTime;

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

    public MStore storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public MStore storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNameKtkn() {
        return storeNameKtkn;
    }

    public MStore storeNameKtkn(String storeNameKtkn) {
        this.storeNameKtkn = storeNameKtkn;
        return this;
    }

    public void setStoreNameKtkn(String storeNameKtkn) {
        this.storeNameKtkn = storeNameKtkn;
    }

    public String getStoreIf() {
        return storeIf;
    }

    public MStore storeIf(String storeIf) {
        this.storeIf = storeIf;
        return this;
    }

    public void setStoreIf(String storeIf) {
        this.storeIf = storeIf;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public MStore storeAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public MStore storePhone(String storePhone) {
        this.storePhone = storePhone;
        return this;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public MStore postNumber(String postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public MStore faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public MStore startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public MStore endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStaffCheck() {
        return staffCheck;
    }

    public MStore staffCheck(String staffCheck) {
        this.staffCheck = staffCheck;
        return this;
    }

    public void setStaffCheck(String staffCheck) {
        this.staffCheck = staffCheck;
    }
    
    public String getCourseBuffetCheck() {
        return courseBuffetCheck;
    }
  
    public MStore courseBuffetCheck(String courseBuffetCheck) {
        this.courseBuffetCheck = courseBuffetCheck;
        return this;
    }
  
    public void setCourseBuffetCheck(String courseBuffetCheck) {
        this.courseBuffetCheck = courseBuffetCheck;
    }

    public String getBusinessId() {
        return businessId;
    }

    public MStore businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public MStore storePassword(String storePassword) {
        this.storePassword = storePassword;
        return this;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getCustomerSelectFlag() {
        return customerSelectFlag;
    }

    public MStore customerSelectFlag(String customerSelectFlag) {
        this.customerSelectFlag = customerSelectFlag;
        return this;
    }

    public void setCustomerSelectFlag(String customerSelectFlag) {
        this.customerSelectFlag = customerSelectFlag;
    }

    public Integer getDefaultUseTime() {
        return defaultUseTime;
    }

    public MStore defaultUseTime(Integer defaultUseTime) {
        this.defaultUseTime = defaultUseTime;
        return this;
    }

    public void setDefaultUseTime(Integer defaultUseTime) {
        this.defaultUseTime = defaultUseTime;
    }

    public Integer getCateringIntervalTime() {
        return cateringIntervalTime;
    }

    public MStore cateringIntervalTime(Integer cateringIntervalTime) {
        this.cateringIntervalTime = cateringIntervalTime;
        return this;
    }

    public void setCateringIntervalTime(Integer cateringIntervalTime) {
        this.cateringIntervalTime = cateringIntervalTime;
    }

    public Integer getTakeoutIntervalTime() {
        return takeoutIntervalTime;
    }

    public MStore takeoutIntervalTime(Integer takeoutIntervalTime) {
        this.takeoutIntervalTime = takeoutIntervalTime;
        return this;
    }

    public void setTakeoutIntervalTime(Integer takeoutIntervalTime) {
        this.takeoutIntervalTime = takeoutIntervalTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public MStore intervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
        return this;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public MStore deliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
        return this;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getDeliveryTypeFlag() {
        return deliveryTypeFlag;
    }

    public MStore deliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
        return this;
    }

    public void setDeliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
    }

    public String getCateringChargeType() {
        return cateringChargeType;
    }

    public MStore cateringChargeType(String cateringChargeType) {
        this.cateringChargeType = cateringChargeType;
        return this;
    }

    public void setCateringChargeType(String cateringChargeType) {
        this.cateringChargeType = cateringChargeType;
    }

    public BigDecimal getCateringChargeAmount() {
        return cateringChargeAmount;
    }

    public MStore cateringChargeAmount(BigDecimal cateringChargeAmount) {
        this.cateringChargeAmount = cateringChargeAmount;
        return this;
    }

    public void setCateringChargeAmount(BigDecimal cateringChargeAmount) {
        this.cateringChargeAmount = cateringChargeAmount;
    }

    public BigDecimal getCateringChargePercent() {
        return cateringChargePercent;
    }

    public MStore cateringChargePercent(BigDecimal cateringChargePercent) {
        this.cateringChargePercent = cateringChargePercent;
        return this;
    }

    public void setCateringChargePercent(BigDecimal cateringChargePercent) {
        this.cateringChargePercent = cateringChargePercent;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public MStore orderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
        return this;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MStore delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MStore insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MStore insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MStore updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MStore updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MStore version(Integer version) {
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
        if (!(o instanceof MStore)) {
            return false;
        }
        return id != null && id.equals(((MStore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MStore{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", storeNameKtkn='" + getStoreNameKtkn() + "'" +
            ", storeIf='" + getStoreIf() + "'" +
            ", storeAddress='" + getStoreAddress() + "'" +
            ", storePhone='" + getStorePhone() + "'" +
            ", postNumber='" + getPostNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", staffCheck='" + getStaffCheck() + "'" +
            ", courseBuffetCheck='" + getCourseBuffetCheck() + "'" +
            ", businessId='" + getBusinessId() + "'" +
            ", storePassword='" + getStorePassword() + "'" +
            ", defaultUseTime=" + getDefaultUseTime() +
            ", customerSelectFlag=" + getCustomerSelectFlag() +
            ", cateringIntervalTime=" + getCateringIntervalTime() +
            ", takeoutIntervalTime=" + getTakeoutIntervalTime() +
            ", intervalTime=" + getIntervalTime() +
            ", deliveryFlag=" + getDeliveryFlag() +
            ", deliveryTypeFlag=" + getDeliveryTypeFlag() +
            ", cateringChargeType=" + getCateringChargeType() +
            ", cateringChargeAmount=" + getCateringChargeAmount() +
            ", cateringChargePercent=" + getCateringChargePercent() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
