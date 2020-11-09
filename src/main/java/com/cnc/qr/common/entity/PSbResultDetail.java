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
 * SB支払履歴テーブルエンティティ.
 */
@Entity
@Table(name = "p_sb_result_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PSbResultDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "sb_result_detail_id")
    private Integer sbResultDetailId;

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

    @Column(name = "pay_price", precision = 21, scale = 2)
    private BigDecimal payPrice;

    @Size(max = 2)
    @Column(name = "resp_code", length = 2)
    private String respCode;

    @Size(max = 2)
    @Column(name = "return_resp_code", length = 2)
    private String returnRespCode;

    @Size(max = 140)
    @Column(name = "res_tracking_id", length = 140)
    private String resTrackingId;

    @Size(max = 150)
    @Column(name = "res_payinfo_key", length = 150)
    private String resPayinfoKey;

    @Size(max = 14)
    @Column(name = "res_payment_date", length = 14)
    private String resPaymentDate;

    @Size(max = 32)
    @Column(name = "res_sps_transaction_id", length = 32)
    private String resSpsTransactionId;

    @Size(max = 14)
    @Column(name = "res_process_date", length = 14)
    private String resProcessDate;

    @Size(max = 100)
    @Column(name = "res_err_code", length = 100)
    private String resErrCode;

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

    public PSbResultDetail storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getSbResultDetailId() {
        return sbResultDetailId;
    }

    public PSbResultDetail sbResultDetailId(Integer sbResultDetailId) {
        this.sbResultDetailId = sbResultDetailId;
        return this;
    }

    public void setSbResultDetailId(Integer sbResultDetailId) {
        this.sbResultDetailId = sbResultDetailId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public PSbResultDetail orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public PSbResultDetail orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public PSbResultDetail orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public PSbResultDetail paymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
        return this;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public PSbResultDetail payPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
        return this;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getRespCode() {
        return respCode;
    }

    public PSbResultDetail respCode(String respCode) {
        this.respCode = respCode;
        return this;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getReturnRespCode() {
        return returnRespCode;
    }

    public PSbResultDetail returnRespCode(String returnRespCode) {
        this.returnRespCode = returnRespCode;
        return this;
    }

    public void setReturnRespCode(String returnRespCode) {
        this.returnRespCode = returnRespCode;
    }


    public String getResTrackingId() {
        return resTrackingId;
    }

    public PSbResultDetail resTrackingId(String resTrackingId) {
        this.resTrackingId = resTrackingId;
        return this;
    }

    public void setResTrackingId(String resTrackingId) {
        this.resTrackingId = resTrackingId;
    }

    public String getResPayinfoKey() {
        return resPayinfoKey;
    }

    public PSbResultDetail resPayinfoKey(String resPayinfoKey) {
        this.resPayinfoKey = resPayinfoKey;
        return this;
    }

    public void setResPayinfoKey(String resPayinfoKey) {
        this.resPayinfoKey = resPayinfoKey;
    }

    public String getResPaymentDate() {
        return resPaymentDate;
    }

    public PSbResultDetail resPaymentDate(String resPaymentDate) {
        this.resPaymentDate = resPaymentDate;
        return this;
    }

    public void setResPaymentDate(String resPaymentDate) {
        this.resPaymentDate = resPaymentDate;
    }

    public String getResSpsTransactionId() {
        return resSpsTransactionId;
    }

    public PSbResultDetail resSpsTransactionId(String resSpsTransactionId) {
        this.resSpsTransactionId = resSpsTransactionId;
        return this;
    }

    public void setResSpsTransactionId(String resSpsTransactionId) {
        this.resSpsTransactionId = resSpsTransactionId;
    }

    public String getResProcessDate() {
        return resProcessDate;
    }

    public PSbResultDetail resProcessDate(String resProcessDate) {
        this.resProcessDate = resProcessDate;
        return this;
    }

    public void setResProcessDate(String resProcessDate) {
        this.resProcessDate = resProcessDate;
    }

    public String getResErrCode() {
        return resErrCode;
    }

    public PSbResultDetail resErrCode(String resErrCode) {
        this.resErrCode = resErrCode;
        return this;
    }

    public void setResErrCode(String resErrCode) {
        this.resErrCode = resErrCode;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public PSbResultDetail delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public PSbResultDetail insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public PSbResultDetail insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public PSbResultDetail updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public PSbResultDetail updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public PSbResultDetail version(Integer version) {
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
        if (!(o instanceof PSbResultDetail)) {
            return false;
        }
        return id != null && id.equals(((PSbResultDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PSbResultDetail{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", sbResultDetailId=" + getSbResultDetailId() +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", orderNo='" + getOrderNo() + "'" +
            ", paymentMethodCode='" + getPaymentMethodCode() + "'" +
            ", payPrice='" + getPayPrice() + "'" +
            ", respCode='" + getRespCode() + "'" +
            ", returnRespCode='" + getReturnRespCode() + "'" +
            ", resTrackingId='" + getResTrackingId() + "'" +
            ", resPayinfoKey='" + getResPayinfoKey() + "'" +
            ", resPaymentDate='" + getResPaymentDate() + "'" +
            ", resSpsTransactionId='" + getResSpsTransactionId() + "'" +
            ", resProcessDate='" + getResProcessDate() + "'" +
            ", resErrCode='" + getResErrCode() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
