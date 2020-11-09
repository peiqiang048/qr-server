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
 * 税額テーブルエンティティ.
 */
@Entity
@Table(name = "o_tax_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OTaxAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "tax_amount_id")
    private Integer taxAmountId;

    @Size(max = 20)
    @Column(name = "order_summary_id", length = 20)
    private String orderSummaryId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "consumption_amount", precision = 10, scale = 0)
    private BigDecimal consumptionAmount;

    @Column(name = "foreign_tax", precision = 10, scale = 0)
    private BigDecimal foreignTax;

    @Column(name = "foreign_normal_amount", precision = 10, scale = 0)
    private BigDecimal foreignNormalAmount;

    @Column(name = "foreign_normal_object_amount", precision = 10, scale = 0)
    private BigDecimal foreignNormalObjectAmount;

    @Column(name = "foreign_relief_amount", precision = 10, scale = 0)
    private BigDecimal foreignReliefAmount;

    @Column(name = "foreign_relief_object_amount", precision = 10, scale = 0)
    private BigDecimal foreignReliefObjectAmount;

    @Column(name = "included_tax", precision = 10, scale = 0)
    private BigDecimal includedTax;

    @Column(name = "included_normal_amount", precision = 10, scale = 0)
    private BigDecimal includedNormalAmount;

    @Column(name = "included_normal_object_amount", precision = 10, scale = 0)
    private BigDecimal includedNormalObjectAmount;

    @Column(name = "included_relief_amount", precision = 10, scale = 0)
    private BigDecimal includedReliefAmount;

    @Column(name = "included_relief_object_amount", precision = 10, scale = 0)
    private BigDecimal includedReliefObjectAmount;

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

    public OTaxAmount storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public Integer getTaxAmountId() {
        return taxAmountId;
    }

    public OTaxAmount taxAmountId(Integer taxAmountId) {
        this.taxAmountId = taxAmountId;
        return this;
    }

    public void setTaxAmountId(Integer taxAmountId) {
        this.taxAmountId = taxAmountId;
    }


    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public OTaxAmount orderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
        return this;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public OTaxAmount orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public BigDecimal getConsumptionAmount() {
        return consumptionAmount;
    }

    public OTaxAmount consumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
        return this;
    }

    public void setConsumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }


    public BigDecimal getForeignTax() {
        return foreignTax;
    }

    public OTaxAmount foreignTax(
        BigDecimal appointedDayFluctuationAmount) {
        this.foreignTax = foreignTax;
        return this;
    }

    public void setForeignTax(BigDecimal foreignTax) {
        this.foreignTax = foreignTax;
    }


    public BigDecimal getForeignNormalAmount() {
        return foreignNormalAmount;
    }

    public OTaxAmount foreignNormalAmount(
        BigDecimal foreignNormalAmount) {
        this.foreignNormalAmount = foreignNormalAmount;
        return this;
    }

    public void setForeignNormalAmount(BigDecimal foreignNormalAmount) {
        this.foreignNormalAmount = foreignNormalAmount;
    }

    public BigDecimal getForeignNormalObjectAmount() {
        return foreignNormalObjectAmount;
    }

    public OTaxAmount foreignNormalObjectAmount(
        BigDecimal foreignNormalObjectAmount) {
        this.foreignNormalObjectAmount = foreignNormalObjectAmount;
        return this;
    }

    public void setForeignNormalObjectAmount(BigDecimal foreignNormalObjectAmount) {
        this.foreignNormalObjectAmount = foreignNormalObjectAmount;
    }


    public BigDecimal getForeignReliefAmount() {
        return foreignReliefAmount;
    }

    public OTaxAmount foreignReliefAmount(
        BigDecimal foreignReliefAmount) {
        this.foreignReliefAmount = foreignReliefAmount;
        return this;
    }

    public void setForeignReliefAmount(BigDecimal foreignReliefAmount) {
        this.foreignReliefAmount = foreignReliefAmount;
    }


    public BigDecimal getForeignReliefObjectAmount() {
        return foreignReliefObjectAmount;
    }

    public OTaxAmount foreignReliefObjectAmount(
        BigDecimal foreignReliefObjectAmount) {
        this.foreignReliefObjectAmount = foreignReliefObjectAmount;
        return this;
    }

    public void setForeignReliefObjectAmount(BigDecimal foreignReliefObjectAmount) {
        this.foreignReliefObjectAmount = foreignReliefObjectAmount;
    }


    public BigDecimal getIncludedTax() {
        return includedTax;
    }

    public OTaxAmount includedTax(
        BigDecimal includedTax) {
        this.includedTax = includedTax;
        return this;
    }

    public void setIncludedTax(BigDecimal includedTax) {
        this.includedTax = includedTax;
    }


    public BigDecimal getIncludedNormalAmount() {
        return includedNormalAmount;
    }

    public OTaxAmount includedNormalAmount(
        BigDecimal includedNormalAmount) {
        this.includedNormalAmount = includedNormalAmount;
        return this;
    }

    public void setIncludedNormalAmount(BigDecimal includedNormalAmount) {
        this.includedNormalAmount = includedNormalAmount;
    }


    public BigDecimal getIncludedNormalObjectAmount() {
        return includedNormalObjectAmount;
    }

    public OTaxAmount includedNormalObjectAmount(
        BigDecimal includedNormalObjectAmount) {
        this.includedNormalObjectAmount = includedNormalObjectAmount;
        return this;
    }

    public void setIncludedNormalObjectAmount(BigDecimal includedNormalObjectAmount) {
        this.includedNormalObjectAmount = includedNormalObjectAmount;
    }


    public BigDecimal getIncludedReliefAmount() {
        return includedReliefAmount;
    }

    public OTaxAmount includedReliefAmount(
        BigDecimal includedReliefAmount) {
        this.includedReliefAmount = includedReliefAmount;
        return this;
    }

    public void setIncludedReliefAmount(BigDecimal includedReliefAmount) {
        this.includedReliefAmount = includedReliefAmount;
    }


    public BigDecimal getIncludedReliefObjectAmount() {
        return includedReliefObjectAmount;
    }

    public OTaxAmount includedReliefObjectAmount(
        BigDecimal includedReliefObjectAmount) {
        this.includedReliefObjectAmount = includedReliefObjectAmount;
        return this;
    }

    public void setIncludedReliefObjectAmount(BigDecimal includedReliefObjectAmount) {
        this.includedReliefObjectAmount = includedReliefObjectAmount;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public OTaxAmount delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OTaxAmount insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OTaxAmount insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OTaxAmount updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OTaxAmount updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OTaxAmount version(Integer version) {
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
        if (!(o instanceof OTaxAmount)) {
            return false;
        }
        return id != null && id.equals(((OTaxAmount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MReceipt{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", taxAmountId='" + getTaxAmountId() + "'" +
            ", orderSummaryId='" + getOrderSummaryId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", consumptionAmount='" + getConsumptionAmount() + "'" +
            ", foreignTax='" + getForeignTax() + "'" +
            ", foreignNormalAmount='" + getForeignNormalAmount() + "'" +
            ", foreignNormalObjectAmount='" + getForeignNormalObjectAmount() + "'" +
            ", foreignReliefAmount='" + getForeignReliefAmount() + "'" +
            ", foreignReliefObjectAmount='" + getForeignReliefObjectAmount() + "'" +
            ", includedTax='" + getIncludedTax() + "'" +
            ", includedNormalAmount='" + getIncludedNormalAmount() + "'" +
            ", includedNormalObjectAmount='" + getIncludedNormalObjectAmount() + "'" +
            ", includedReliefAmount='" + getIncludedReliefAmount() + "'" +
            ", includedReliefObjectAmount='" + getIncludedReliefObjectAmount() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
