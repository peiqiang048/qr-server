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
 * 出前注文サマリテーブルエンティティ.
 */
@Entity
@Table(name = "o_delivery_order_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ODeliveryOrderSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Size(max = 20)
    @Column(name = "order_summary_id", length = 20)
    private String orderSummaryId;

    @Size(max = 20)
    @Column(name = "receivables_id", length = 20)
    private String receivablesId;

    @Column(name = "order_amount", precision = 21, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "catering_charge_amount", precision = 21, scale = 2)
    private BigDecimal cateringChargeAmount;

    @Column(name = "payment_amount", precision = 21, scale = 2)
    private BigDecimal paymentAmount;

    @Size(max = 1)
    @Column(name = "delivery_type_flag", length = 1)
    private String deliveryTypeFlag;

    @Size(max = 1)
    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Size(max = 200)
    @Column(name = "customer_name", length = 200)
    private String customerName;

    @Size(max = 15)
    @Column(name = "tel_number", length = 15)
    private String telNumber;

    @Size(max = 2)
    @Column(name = "prefecture_id", length = 2)
    private String prefectureId;

    @Size(max = 3)
    @Column(name = "city_id", length = 3)
    private String cityId;

    @Size(max = 5)
    @Column(name = "block_id", length = 4)
    private String blockId;

    @Size(max = 400)
    @Column(name = "delivery_other", length = 400)
    private String deliveryOther;

    @Size(max = 100)
    @Column(name = "mail_address", length = 100)
    private String mailAddress;

    @Size(max = 200)
    @Column(name = "comment", length = 200)
    private String comment;

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

    public ODeliveryOrderSummary storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public ODeliveryOrderSummary orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public String getReceivablesId() {
        return receivablesId;
    }

    public ODeliveryOrderSummary receivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
        return this;
    }

    public void setReceivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public ODeliveryOrderSummary orderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getCateringChargeAmount() {
        return cateringChargeAmount;
    }

    public ODeliveryOrderSummary cateringChargeAmount(BigDecimal cateringChargeAmount) {
        this.cateringChargeAmount = cateringChargeAmount;
        return this;
    }

    public void setCateringChargeAmount(BigDecimal cateringChargeAmount) {
        this.cateringChargeAmount = cateringChargeAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public ODeliveryOrderSummary paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getDeliveryTypeFlag() {
        return deliveryTypeFlag;
    }

    public ODeliveryOrderSummary deliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
        return this;
    }

    public void setDeliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
    }

    public String getStatus() {
        return status;
    }

    public ODeliveryOrderSummary status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ODeliveryOrderSummary startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public ODeliveryOrderSummary endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ODeliveryOrderSummary customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public ODeliveryOrderSummary telNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getPrefectureId() {
        return prefectureId;
    }

    public ODeliveryOrderSummary prefectureId(String prefectureId) {
        this.prefectureId = prefectureId;
        return this;
    }

    public void setPrefectureId(String prefectureId) {
        this.prefectureId = prefectureId;
    }

    public String getCityId() {
        return cityId;
    }

    public ODeliveryOrderSummary cityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBlockId() {
        return blockId;
    }

    public ODeliveryOrderSummary blockId(String blockId) {
        this.blockId = blockId;
        return this;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getDeliveryOther() {
        return deliveryOther;
    }

    public ODeliveryOrderSummary deliveryOther(String deliveryOther) {
        this.deliveryOther = deliveryOther;
        return this;
    }

    public void setDeliveryOther(String deliveryOther) {
        this.deliveryOther = deliveryOther;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public ODeliveryOrderSummary mailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
        return this;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getComment() {
        return comment;
    }

    public ODeliveryOrderSummary comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public ODeliveryOrderSummary delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public ODeliveryOrderSummary insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public ODeliveryOrderSummary insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public ODeliveryOrderSummary updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public ODeliveryOrderSummary updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public ODeliveryOrderSummary version(Integer version) {
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
        if (!(o instanceof ODeliveryOrderSummary)) {
            return false;
        }
        return id != null && id.equals(((ODeliveryOrderSummary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ODeliveryOrderSummary{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", receivablesId='" + getReceivablesId() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", cateringChargeAmount=" + getCateringChargeAmount() +
            ", paymentAmount=" + getPaymentAmount() +
            ", deliveryTypeFlag=" + getDeliveryTypeFlag() +
            ", status=" + getStatus() +
            ", startTime=" + getStartTime() +
            ", endTime=" + getEndTime() +
            ", customerName=" + getCustomerName() +
            ", telNumber=" + getTelNumber() +
            ", prefectureId=" + getPrefectureId() +
            ", cityId=" + getCityId() +
            ", blockId=" + getBlockId() +
            ", deliveryOther=" + getDeliveryOther() +
            ", mailAddress=" + getMailAddress() +
            ", comment=" + getComment() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
