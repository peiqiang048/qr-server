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
 * トリオ支払履歴テーブルエンティティ.
 */
@Entity
@Table(name = "p_trio_result_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PTrioResultDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "trio_result_detail_id")
    private Integer trioResultDetailId;

    @Size(max = 20)
    @Column(name = "order_summary_id", length = 20)
    private String orderSummaryId;

    @Column(name = "order_id")
    private Integer orderId;

    @Size(max = 52)
    @Column(name = "order_no", length = 52)
    private String orderNo;

    @Size(max = 2)
    @Column(name = "payment_method_code", length = 2)
    private String paymentMethodCode;

    @Size(max = 17)
    @Column(name = "cancel_order_id", length = 17)
    private String cancelOrderId;

    @Size(max = 17)
    @Column(name = "return_order_id", length = 17)
    private String returnOrderId;

    @Column(name = "pay_price", precision = 21, scale = 2)
    private BigDecimal payPrice;

    @Size(max = 2)
    @Column(name = "resp_code", length = 2)
    private String respCode;

    @Size(max = 2)
    @Column(name = "cancel_resp_code", length = 2)
    private String cancelRespCode;

    @Size(max = 2)
    @Column(name = "return_resp_code", length = 2)
    private String returnRespCode;

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

    public PTrioResultDetail storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getTrioResultDetailId() {
        return trioResultDetailId;
    }

    public PTrioResultDetail trioResultDetailId(Integer trioResultDetailId) {
        this.trioResultDetailId = trioResultDetailId;
        return this;
    }

    public void setTrioResultDetailId(Integer trioResultDetailId) {
        this.trioResultDetailId = trioResultDetailId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public PTrioResultDetail orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public PTrioResultDetail orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public PTrioResultDetail orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public PTrioResultDetail paymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
        return this;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getCancelOrderId() {
        return cancelOrderId;
    }

    public PTrioResultDetail cancelOrderId(String cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
        return this;
    }

    public void setCancelOrderId(String cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public PTrioResultDetail payPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
        return this;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public PTrioResultDetail returnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
        return this;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getRespCode() {
        return respCode;
    }

    public PTrioResultDetail respCode(String respCode) {
        this.respCode = respCode;
        return this;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getCancelRespCode() {
        return cancelRespCode;
    }

    public PTrioResultDetail cancelRespCode(String cancelRespCode) {
        this.cancelRespCode = cancelRespCode;
        return this;
    }

    public void setCancelRespCode(String cancelRespCode) {
        this.cancelRespCode = cancelRespCode;
    }

    public String getReturnRespCode() {
        return returnRespCode;
    }

    public PTrioResultDetail returnRespCode(String returnRespCode) {
        this.returnRespCode = returnRespCode;
        return this;
    }

    public void setReturnRespCode(String returnRespCode) {
        this.returnRespCode = returnRespCode;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public PTrioResultDetail delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public PTrioResultDetail insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public PTrioResultDetail insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public PTrioResultDetail updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public PTrioResultDetail updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public PTrioResultDetail version(Integer version) {
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
        if (!(o instanceof PTrioResultDetail)) {
            return false;
        }
        return id != null && id.equals(((PTrioResultDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PTrioResultDetail{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", trioResultDetailId=" + getTrioResultDetailId() +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", orderNo='" + getOrderNo() + "'" +
            ", paymentMethodCode='" + getPaymentMethodCode() + "'" +
            ", cancelOrderId='" + getCancelOrderId() + "'" +
            ", returnOrderId='" + getReturnOrderId() + "'" +
            ", payPrice='" + getPayPrice() + "'" +
            ", respCode='" + getRespCode() + "'" +
            ", cancelRespCode='" + getCancelRespCode() + "'" +
            ", returnRespCode='" + getReturnRespCode() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
