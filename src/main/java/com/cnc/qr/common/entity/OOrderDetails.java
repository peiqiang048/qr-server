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
 * 注文明細テーブルエンティティ.
 */
@Entity
@Table(name = "o_order_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_price", precision = 21, scale = 2)
    private BigDecimal itemPrice;

    @Column(name = "item_count")
    private Integer itemCount;

    @Size(max = 1)
    @Column(name = "item_classification", length = 1)
    private String itemClassification;

    @Size(max = 2)
    @Column(name = "item_status", length = 2)
    private String itemStatus;


    @Column(name = "item_return_id", length = 30)
    private String itemReturnId;

    @Column(name = "return_order_detail_id")
    private Integer returnOrderDetailId;

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

    public OOrderDetails storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public OOrderDetails orderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
        return this;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OOrderDetails orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public OOrderDetails itemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public OOrderDetails itemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public OOrderDetails itemCount(Integer itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemClassification() {
        return itemClassification;
    }

    public OOrderDetails itemClassification(String itemClassification) {
        this.itemClassification = itemClassification;
        return this;
    }

    public void setItemClassification(String itemClassification) {
        this.itemClassification = itemClassification;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public OOrderDetails itemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
        return this;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemReturnId() {
        return itemReturnId;
    }

    public OOrderDetails itemReturnId(String itemReturnId) {
        this.itemReturnId = itemReturnId;
        return this;
    }

    public void setItemReturnId(String itemReturnId) {
        this.itemReturnId = itemReturnId;
    }

    public Integer getReturnOrderDetailId() {
        return returnOrderDetailId;
    }

    public OOrderDetails returnOrderDetailId(Integer returnOrderDetailId) {
        this.returnOrderDetailId = returnOrderDetailId;
        return this;
    }

    public void setReturnOrderDetailId(Integer returnOrderDetailId) {
        this.returnOrderDetailId = returnOrderDetailId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public OOrderDetails delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public OOrderDetails insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public OOrderDetails insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public OOrderDetails updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public OOrderDetails updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public OOrderDetails version(Integer version) {
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
        if (!(o instanceof OOrderDetails)) {
            return false;
        }
        return id != null && id.equals(((OOrderDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OOrderDetails{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", orderDetailId=" + getOrderDetailId() +
            ", orderId=" + getOrderId() +
            ", itemId=" + getItemId() +
            ", itemPrice=" + getItemPrice() +
            ", itemCount=" + getItemCount() +
            ", itemClassification=" + getItemClassification() +
            ", itemStatus=" + getItemStatus() +
            ", itemReturnId=" + getItemReturnId() +
            ", returnOrderDetailId=" + getReturnOrderDetailId() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
