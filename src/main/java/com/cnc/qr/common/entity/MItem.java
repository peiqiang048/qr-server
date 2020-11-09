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
 * 商品マスタエンティティ.
 */
@Entity
@Table(name = "m_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "kitchen_id")
    private Integer kitchenId;

    @Size(max = 1)
    @Column(name = "option_flag", length = 1)
    private String optionFlag;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price", precision = 21, scale = 2)
    private BigDecimal itemPrice;

    @Column(name = "item_info")
    private String itemInfo;

    @Size(max = 2)
    @Column(name = "status", length = 2)
    private String status;

    @Column(name = "tax_id")
    private Integer taxId;

    @Size(max = 1)
    @Column(name = "delivery_flag", length = 1)
    private String deliveryFlag;

    @Size(max = 1)
    @Column(name = "delivery_type_flag", length = 1)
    private String deliveryTypeFlag;

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

    @Size(max = 2)
    @Column(name = "item_type", length = 2)
    private String itemType;

    @Column(name = "buffet_time")
    private Integer buffetTime;

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

    public MItem storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public MItem itemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public MItem unitId(Integer unitId) {
        this.unitId = unitId;
        return this;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public MItem kitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
        return this;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getOptionFlag() {
        return optionFlag;
    }

    public MItem optionFlag(String optionFlag) {
        this.optionFlag = optionFlag;
        return this;
    }

    public void setOptionFlag(String optionFlag) {
        this.optionFlag = optionFlag;
    }

    public String getItemName() {
        return itemName;
    }

    public MItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public MItem itemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public MItem itemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
        return this;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getStatus() {
        return status;
    }

    public MItem status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBuffetTime() {
        return buffetTime;
    }

    public MItem buffetTime(Integer buffetTime) {
        this.buffetTime = buffetTime;
        return this;
    }

    public void setBuffetTime(Integer buffetTime) {
        this.buffetTime = buffetTime;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public MItem taxId(Integer taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public MItem deliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
        return this;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getDeliveryTypeFlag() {
        return deliveryTypeFlag;
    }

    public MItem deliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
        return this;
    }

    public void setDeliveryTypeFlag(String deliveryTypeFlag) {
        this.deliveryTypeFlag = deliveryTypeFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MItem delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MItem insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MItem insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MItem updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MItem updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MItem version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getItemType() {
        return itemType;
    }

    public MItem itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MItem)) {
            return false;
        }
        return id != null && id.equals(((MItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MItem{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", itemId=" + getItemId() +
            ", unitId=" + getUnitId() +
            ", kitchenId=" + getKitchenId() +
            ", optionFlag='" + getOptionFlag() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", itemPrice=" + getItemPrice() +
            ", itemInfo='" + getItemInfo() + "'" +
            ", status='" + getStatus() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", buffetTime='" + getBuffetTime() + "'" +
            ", deliveryFlag=" + getDeliveryFlag() +
            ", deliveryTypeFlag=" + getDeliveryTypeFlag() +
            ", taxId=" + getTaxId() +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
