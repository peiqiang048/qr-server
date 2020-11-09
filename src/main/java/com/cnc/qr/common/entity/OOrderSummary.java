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
 * 注文サマリテーブルエンティティ.
 */
@Entity
@Table(name = "o_order_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OOrderSummary implements Serializable {

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

    @Column(name = "customer_count")
    private Integer customerCount;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "order_amount", precision = 21, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "price_discount_amount", precision = 21, scale = 2)
    private BigDecimal priceDiscountAmount;

    @Column(name = "price_discount_rate", precision = 21, scale = 2)
    private BigDecimal priceDiscountRate;

    @Column(name = "payment_amount", precision = 21, scale = 2)
    private BigDecimal paymentAmount;

    @Size(max = 1)
    @Column(name = "takeout_flag", length = 1)
    private String takeoutFlag;

    @Size(max = 2)
    @Column(name = "order_status", length = 2)
    private String orderStatus;

    @Size(max = 2)
    @Column(name = "payment_type", length = 2)
    private String paymentType;

    @Size(max = 1)
    @Column(name = "seat_release", length = 1)
    private String seatRelease;

    @Size(max = 20)
    @Column(name = "original_order_summary_id", length = 20)
    private String originalOrderSummaryId;

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

    public OOrderSummary storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public OOrderSummary orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public String getOriginalOrderSummaryId() {
        return originalOrderSummaryId;
    }

    public OOrderSummary originalOrderSummaryId(String originalOrderSummaryId) {
        this.originalOrderSummaryId = originalOrderSummaryId;
        return this;
    }

    public void setOriginalOrderSummaryId(String originalOrderSummaryId) {
        this.originalOrderSummaryId = originalOrderSummaryId;
    }

    public String getReceivablesId() {
        return receivablesId;
    }

    public OOrderSummary receivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
        return this;
    }

    public void setReceivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public OOrderSummary customerCount(Integer customerCount) {
        this.customerCount = customerCount;
        return this;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public Integer getTableId() {
        return tableId;
    }

    public OOrderSummary tableId(Integer tableId) {
        this.tableId = tableId;
        return this;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public OOrderSummary orderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPriceDiscountAmount() {
        return priceDiscountAmount;
    }

    public OOrderSummary priceDiscountAmount(BigDecimal priceDiscountAmount) {
        this.priceDiscountAmount = priceDiscountAmount;
        return this;
    }

    public void setPriceDiscountAmount(BigDecimal priceDiscountAmount) {
        this.priceDiscountAmount = priceDiscountAmount;
    }

    public BigDecimal getPriceDiscountRate() {
        return priceDiscountRate;
    }

    public OOrderSummary priceDiscountRate(BigDecimal priceDiscountRate) {
        this.priceDiscountRate = priceDiscountRate;
        return this;
    }

    public void setPriceDiscountRate(BigDecimal priceDiscountRate) {
        this.priceDiscountRate = priceDiscountRate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public OOrderSummary paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTakeoutFlag() {
        return takeoutFlag;
    }

    public OOrderSummary takeoutFlag(String takeoutFlag) {
        this.takeoutFlag = takeoutFlag;
        return this;
    }

    public void setTakeoutFlag(String takeoutFlag) {
        this.takeoutFlag = takeoutFlag;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public OOrderSummary orderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public OOrderSummary paymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSeatRelease() {
        return seatRelease;
    }

    public OOrderSummary seatRelease(String seatRelease) {
        this.seatRelease = seatRelease;
        return this;
    }

    public void setSeatRelease(String seatRelease) {
        this.seatRelease = seatRelease;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OOrderSummary delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OOrderSummary insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OOrderSummary insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OOrderSummary updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OOrderSummary updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OOrderSummary version(Integer version) {
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
        if (!(o instanceof OOrderSummary)) {
            return false;
        }
        return id != null && id.equals(((OOrderSummary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OOrderSummary{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", originalOrderSummaryId='" + getOriginalOrderSummaryId() + "'" +
            ", receivablesId='" + getReceivablesId() + "'" +
            ", customerCount=" + getCustomerCount() +
            ", tableId='" + getTableId() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", priceDiscountAmount=" + getPriceDiscountAmount() +
            ", priceDiscountRate=" + getPriceDiscountRate() +
            ", paymentAmount=" + getPaymentAmount() +
            ", takeoutFlag='" + getTakeoutFlag() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", seatRelease='" + getSeatRelease() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
