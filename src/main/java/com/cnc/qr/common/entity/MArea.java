package com.cnc.qr.common.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 区域マスタエンティティ.
 */
@Entity
@Table(name = "m_area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 2)
    @Column(name = "prefecture_id", length = 2)
    private String prefectureId;

    @Size(max = 3)
    @Column(name = "city_id", length = 3)
    private String cityId;

    @Size(max = 5)
    @Column(name = "block_id", length = 4)
    private String blockId;

    @Column(name = "prefecture_name")
    private String prefectureName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "block_name")
    private String blockName;

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


    public String getPrefectureId() {
        return prefectureId;
    }

    public MArea prefectureId(String prefectureId) {
        this.prefectureId = prefectureId;
        return this;
    }

    public void setPrefectureId(String prefectureId) {
        this.prefectureId = prefectureId;
    }

    public String getCityId() {
        return cityId;
    }

    public MArea cityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBlockId() {
        return blockId;
    }

    public MArea blockId(String blockId) {
        this.blockId = blockId;
        return this;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getPrefectureName() {
        return prefectureName;
    }

    public MArea prefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
        return this;
    }

    public void setPrefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
    }

    public String getCityName() {
        return cityName;
    }

    public MArea cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBlockName() {
        return blockName;
    }

    public MArea blockName(String blockName) {
        this.blockName = blockName;
        return this;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MArea delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MArea insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MArea insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MArea updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MArea updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MArea version(Integer version) {
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
        if (!(o instanceof MArea)) {
            return false;
        }
        return id != null && id.equals(((MArea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MArea{" +
            "id=" + getId() +
            ", prefectureId='" + getPrefectureId() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", blockId='" + getBlockId() + "'" +
            ", prefectureName='" + getPrefectureName() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", blockName='" + getBlockName() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }


}
