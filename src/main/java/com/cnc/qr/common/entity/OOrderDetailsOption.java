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
 * 注文明細オプションテーブルエンティティ.
 */
@Entity
@Table(name = "o_order_details_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OOrderDetailsOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @Size(max = 4)
    @Column(name = "item_option_type_code", length = 4)
    private String itemOptionTypeCode;

    @Size(max = 4)
    @Column(name = "item_option_code", length = 2)
    private String itemOptionCode;

    @Column(name = "diff_price", precision = 21, scale = 2)
    private BigDecimal diffPrice;

    @Column(name = "item_option_count")
    private Integer itemOptionCount;

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

    public OOrderDetailsOption storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public OOrderDetailsOption orderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
        return this;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getItemOptionTypeCode() {
        return itemOptionTypeCode;
    }

    public OOrderDetailsOption itemOptionTypeCode(String itemOptionTypeCode) {
        this.itemOptionTypeCode = itemOptionTypeCode;
        return this;
    }

    public void setItemOptionTypeCode(String itemOptionTypeCode) {
        this.itemOptionTypeCode = itemOptionTypeCode;
    }

    public String getItemOptionCode() {
        return itemOptionCode;
    }

    public OOrderDetailsOption itemOptionCode(String itemOptionCode) {
        this.itemOptionCode = itemOptionCode;
        return this;
    }

    public void setItemOptionCode(String itemOptionCode) {
        this.itemOptionCode = itemOptionCode;
    }

    public BigDecimal getDiffPrice() {
        return diffPrice;
    }

    public OOrderDetailsOption diffPrice(BigDecimal diffPrice) {
        this.diffPrice = diffPrice;
        return this;
    }

    public void setDiffPrice(BigDecimal diffPrice) {
        this.diffPrice = diffPrice;
    }

    public Integer getItemOptionCount() {
        return itemOptionCount;
    }

    public OOrderDetailsOption itemOptionCount(Integer itemOptionCount) {
        this.itemOptionCount = itemOptionCount;
        return this;
    }

    public void setItemOptionCount(Integer itemOptionCount) {
        this.itemOptionCount = itemOptionCount;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OOrderDetailsOption delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OOrderDetailsOption insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OOrderDetailsOption insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OOrderDetailsOption updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OOrderDetailsOption updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OOrderDetailsOption version(Integer version) {
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
        if (!(o instanceof OOrderDetailsOption)) {
            return false;
        }
        return id != null && id.equals(((OOrderDetailsOption) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OOrderDetailsOption{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", orderDetailId=" + getOrderDetailId() +
            ", itemOptionTypeCode='" + getItemOptionTypeCode() + "'" +
            ", itemOptionCode='" + getItemOptionCode() + "'" +
            ", diffPrice=" + getDiffPrice() +
            ", itemOptionCount=" + getItemOptionCount() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
