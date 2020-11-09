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
 * 注文テーブルエンティティ.
 */
@Entity
@Table(name = "o_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "order_id")
    private Integer orderId;

    @Size(max = 20)
    @Column(name = "order_summary_id", length = 20)
    private String orderSummaryId;

    @Size(max = 2)
    @Column(name = "pay_status", length = 2)
    private String payStatus;

    @Column(name = "foreign_tax", precision = 21, scale = 2)
    private BigDecimal foreignTax;

    @Column(name = "order_time")
    private ZonedDateTime orderTime;

    @Size(max = 2)
    @Column(name = "order_type", length = 2)
    private String orderType;

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

    public OOrder storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OOrder orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public OOrder orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public OOrder payStatus(String payStatus) {
        this.payStatus = payStatus;
        return this;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getForeignTax() {
        return foreignTax;
    }

    public OOrder foreignTax(BigDecimal foreignTax) {
        this.foreignTax = foreignTax;
        return this;
    }

    public void setForeignTax(BigDecimal foreignTax) {
        this.foreignTax = foreignTax;
    }

    public ZonedDateTime getOrderTime() {
        return orderTime;
    }

    public OOrder orderTime(ZonedDateTime orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public void setOrderTime(ZonedDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public OOrder orderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getComment() {
        return comment;
    }

    public OOrder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OOrder delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OOrder insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OOrder insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OOrder updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OOrder updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OOrder version(Integer version) {
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
        if (!(o instanceof OOrder)) {
            return false;
        }
        return id != null && id.equals(((OOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OOrder{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", orderId=" + getOrderId() +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", payStatus='" + getPayStatus() + "'" +
            ", foreignTax='" + getForeignTax() + "'" +
            ", orderTime='" + getOrderTime() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", comment='" + getComment() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
