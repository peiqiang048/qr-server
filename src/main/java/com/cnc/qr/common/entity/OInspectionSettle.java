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
 * 点検精算テーブルエンティティ.
 */
@Entity
@Table(name = "o_inspection_settle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OInspectionSettle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "inspection_settle_date")
    private ZonedDateTime inspectionSettleDate;

    @Column(name = "previous_day_transferred_amount", precision = 10, scale = 0)
    private BigDecimal previousDayTransferredAmount;

    @Column(name = "today_fluctuation_amount", precision = 10, scale = 0)
    private BigDecimal todayFluctuationAmount;

    @Column(name = "practical_cash_register_amount", precision = 10, scale = 0)
    private BigDecimal practicalCashRegisterAmount;

    @Column(name = "calculation_cash_register_amount", precision = 10, scale = 0)
    private BigDecimal calculationCashRegisterAmount;

    @Column(name = "cash_difference_amount", precision = 10, scale = 0)
    private BigDecimal cashDifferenceAmount;

    @Column(name = "out_amount", precision = 10, scale = 0)
    private BigDecimal outAmount;

    @Column(name = "bank_deposit_amount", precision = 10, scale = 0)
    private BigDecimal bankDepositAmount;

    @Column(name = "next_day_transferred_amount", precision = 10, scale = 0)
    private BigDecimal nextDayTransferredAmount;

    @Column(name = "purchasing_cost", precision = 10, scale = 0)
    private BigDecimal purchasingCost;

    @Size(max = 1)
    @Column(name = "settle_type", length = 1)
    private String settleType;

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

    public OInspectionSettle storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public ZonedDateTime getInspectionSettleDate() {
        return inspectionSettleDate;
    }

    public OInspectionSettle inspectionSettleDate(ZonedDateTime inspectionSettleDate) {
        this.inspectionSettleDate = inspectionSettleDate;
        return this;
    }

    public void setInspectionSettleDate(ZonedDateTime inspectionSettleDate) {
        this.inspectionSettleDate = inspectionSettleDate;
    }


    public BigDecimal getPreviousDayTransferredAmount() {
        return previousDayTransferredAmount;
    }

    public OInspectionSettle previousDayTransferredAmount(BigDecimal previousDayTransferredAmount) {
        this.previousDayTransferredAmount = previousDayTransferredAmount;
        return this;
    }

    public void setPreviousDayTransferredAmount(BigDecimal previousDayTransferredAmount) {
        this.previousDayTransferredAmount = previousDayTransferredAmount;
    }


    public BigDecimal getTodayFluctuationAmount() {
        return todayFluctuationAmount;
    }

    public OInspectionSettle todayFluctuationAmount(
        BigDecimal todayFluctuationAmount) {
        this.todayFluctuationAmount = todayFluctuationAmount;
        return this;
    }

    public void setTodayFluctuationAmount(BigDecimal todayFluctuationAmount) {
        this.todayFluctuationAmount = todayFluctuationAmount;
    }


    public BigDecimal getPracticalCashRegisterAmount() {
        return practicalCashRegisterAmount;
    }

    public OInspectionSettle practicalCashRegisterAmount(
        BigDecimal practicalCashRegisterAmount) {
        this.practicalCashRegisterAmount = practicalCashRegisterAmount;
        return this;
    }

    public void setPracticalCashRegisterAmount(BigDecimal practicalCashRegisterAmount) {
        this.practicalCashRegisterAmount = practicalCashRegisterAmount;
    }


    public BigDecimal getCalculationCashRegisterAmount() {
        return calculationCashRegisterAmount;
    }

    public OInspectionSettle calculationCashRegisterAmount(
        BigDecimal calculationCashRegisterAmount) {
        this.calculationCashRegisterAmount = calculationCashRegisterAmount;
        return this;
    }

    public void setCalculationCashRegisterAmount(BigDecimal calculationCashRegisterAmount) {
        this.calculationCashRegisterAmount = calculationCashRegisterAmount;
    }


    public BigDecimal getCashDifferenceAmount() {
        return cashDifferenceAmount;
    }

    public OInspectionSettle cashDifferenceAmount(
        BigDecimal cashDifferenceAmount) {
        this.cashDifferenceAmount = cashDifferenceAmount;
        return this;
    }

    public void setCashDifferenceAmount(BigDecimal cashDifferenceAmount) {
        this.cashDifferenceAmount = cashDifferenceAmount;
    }


    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public OInspectionSettle outAmount(
        BigDecimal outAmount) {
        this.outAmount = outAmount;
        return this;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }


    public BigDecimal getBankDepositAmount() {
        return bankDepositAmount;
    }

    public OInspectionSettle bankDepositAmount(
        BigDecimal bankDepositAmount) {
        this.bankDepositAmount = bankDepositAmount;
        return this;
    }

    public void setBankDepositAmount(BigDecimal bankDepositAmount) {
        this.bankDepositAmount = bankDepositAmount;
    }


    public BigDecimal getNextDayTransferredAmount() {
        return nextDayTransferredAmount;
    }

    public OInspectionSettle nextDayTransferredAmount(
        BigDecimal nextDayTransferredAmount) {
        this.nextDayTransferredAmount = nextDayTransferredAmount;
        return this;
    }

    public void setNextDayTransferredAmount(BigDecimal nextDayTransferredAmount) {
        this.nextDayTransferredAmount = nextDayTransferredAmount;
    }


    public BigDecimal getPurchasingCost() {
        return purchasingCost;
    }

    public OInspectionSettle purchasingCost(
        BigDecimal purchasingCost) {
        this.purchasingCost = purchasingCost;
        return this;
    }

    public void setPurchasingCost(BigDecimal purchasingCost) {
        this.purchasingCost = purchasingCost;
    }

    public String getSettleType() {
        return settleType;
    }

    public OInspectionSettle settleType(String settleType) {
        this.settleType = settleType;
        return this;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OInspectionSettle delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OInspectionSettle insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OInspectionSettle insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OInspectionSettle updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OInspectionSettle updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OInspectionSettle version(Integer version) {
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
        if (!(o instanceof OInspectionSettle)) {
            return false;
        }
        return id != null && id.equals(((OInspectionSettle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OInspectionSettle{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", inspectionSettleDate='" + getInspectionSettleDate() + "'" +
            ", previousDayTransferredAmount='" + getPreviousDayTransferredAmount() + "'" +
            ", todayFluctuationAmount='" + getTodayFluctuationAmount() + "'" +
            ", practicalCashRegisterAmount='" + getPracticalCashRegisterAmount() + "'" +
            ", calculationCashRegisterAmount='" + getCalculationCashRegisterAmount() + "'" +
            ", cashDifferenceAmount='" + getCashDifferenceAmount() + "'" +
            ", outAmount='" + getOutAmount() + "'" +
            ", bankDepositAmount='" + getBankDepositAmount() + "'" +
            ", nextDayTransferredAmount='" + getNextDayTransferredAmount() + "'" +
            ", purchasingCost='" + getPurchasingCost() + "'" +
            ", settleType='" + getSettleType() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
