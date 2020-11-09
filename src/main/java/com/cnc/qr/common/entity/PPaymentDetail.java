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
 * 支払明細テーブルエンティティ.
 */
@Entity
@Table(name = "p_payment_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PPaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "payment_detail_id")
    private Integer paymentDetailId;

    @Column(name = "payment_id")
    private Integer paymentId;

    @Size(max = 20)
    @Column(name = "order_summary_id", length = 20)
    private String orderSummaryId;

    @Column(name = "order_id")
    private Integer orderId;

    @Size(max = 2)
    @Column(name = "payment_method_code", length = 2)
    private String paymentMethodCode;

    @Column(name = "payment_amount", precision = 21, scale = 2)
    private BigDecimal paymentAmount;

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

    public PPaymentDetail storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public PPaymentDetail paymentId(Integer paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentDetailId() {
        return paymentDetailId;
    }

    public PPaymentDetail paymentDetailId(Integer paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
        return this;
    }

    public void setPaymentDetailId(Integer paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public PPaymentDetail orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public PPaymentDetail orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public PPaymentDetail paymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
        return this;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public PPaymentDetail paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public PPaymentDetail delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public PPaymentDetail insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public PPaymentDetail insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public PPaymentDetail updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public PPaymentDetail updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public PPaymentDetail version(Integer version) {
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
        if (!(o instanceof PPaymentDetail)) {
            return false;
        }
        return id != null && id.equals(((PPaymentDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PPaymentDetail{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", paymentDetailId=" + getPaymentDetailId() +
            ", paymentId=" + getPaymentId() +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", orderId=" + getOrderId() +
            ", paymentMethodCode=" + getPaymentMethodCode() +
            ", paymentAmount='" + getPaymentAmount() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
