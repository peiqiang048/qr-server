package com.cnc.qr.common.entity;

import java.io.Serializable;
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
 * 商品索引マスタエンティティ.
 */
@Entity
@Table(name = "m_item_index")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MItemIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Column(name = "item_index_id")
    private Integer itemIndexId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_name_short")
    private String itemNameShort;

    @Size(max = 100)
    @Column(name = "lower_case", length = 100)
    private String lowerCase;

    @Size(max = 100)
    @Column(name = "capital_case", length = 100)
    private String capitalCase;

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

    public MItemIndex storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getItemIndexId() {
        return itemIndexId;
    }

    public MItemIndex itemIndexId(Integer itemIndexId) {
        this.itemIndexId = itemIndexId;
        return this;
    }

    public void setItemIndexId(Integer itemIndexId) {
        this.itemIndexId = itemIndexId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public MItemIndex itemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemNameShort() {
        return itemNameShort;
    }

    public MItemIndex itemNameShort(String itemNameShort) {
        this.itemNameShort = itemNameShort;
        return this;
    }

    public void setItemNameShort(String itemNameShort) {
        this.itemNameShort = itemNameShort;
    }

    public String getLowerCase() {
        return lowerCase;
    }

    public MItemIndex lowerCase(String lowerCase) {
        this.lowerCase = lowerCase;
        return this;
    }

    public void setLowerCase(String lowerCase) {
        this.lowerCase = lowerCase;
    }

    public String getCapitalCase() {
        return capitalCase;
    }

    public MItemIndex capitalCase(String capitalCase) {
        this.capitalCase = capitalCase;
        return this;
    }

    public void setCapitalCase(String capitalCase) {
        this.capitalCase = capitalCase;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MItemIndex delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MItemIndex insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MItemIndex insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MItemIndex updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MItemIndex updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MItemIndex version(Integer version) {
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
        if (!(o instanceof MItemIndex)) {
            return false;
        }
        return id != null && id.equals(((MItemIndex) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MItemIndex{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", itemIndexId=" + getItemIndexId() +
            ", itemId=" + getItemId() +
            ", itemNameShort='" + getItemNameShort() + "'" +
            ", lowerCase='" + getLowerCase() + "'" +
            ", capitalCase='" + getCapitalCase() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
