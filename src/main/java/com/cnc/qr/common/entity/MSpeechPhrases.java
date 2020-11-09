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
 * 音声認識マスタエンティティ.
 */
@Entity
@Table(name = "m_speech_phrases")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MSpeechPhrases implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "store_id", length = 10)
    private String storeId;

    @Size(max = 100)
    @Column(name = "phrases_id", length = 100)
    private String phrasesId;

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

    public MSpeechPhrases storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPhrasesId() {
        return phrasesId;
    }

    public MSpeechPhrases phrasesId(String phrasesId) {
        this.phrasesId = phrasesId;
        return this;
    }

    public void setPhrasesId(String phrasesId) {
        this.phrasesId = phrasesId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public MSpeechPhrases delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getInsOperCd() {
        return insOperCd;
    }

    public MSpeechPhrases insOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
        return this;
    }

    public void setInsOperCd(String insOperCd) {
        this.insOperCd = insOperCd;
    }

    public ZonedDateTime getInsDateTime() {
        return insDateTime;
    }

    public MSpeechPhrases insDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
        return this;
    }

    public void setInsDateTime(ZonedDateTime insDateTime) {
        this.insDateTime = insDateTime;
    }

    public String getUpdOperCd() {
        return updOperCd;
    }

    public MSpeechPhrases updOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
        return this;
    }

    public void setUpdOperCd(String updOperCd) {
        this.updOperCd = updOperCd;
    }

    public ZonedDateTime getUpdDateTime() {
        return updDateTime;
    }

    public MSpeechPhrases updDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
        return this;
    }

    public void setUpdDateTime(ZonedDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public MSpeechPhrases version(Integer version) {
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
        if (!(o instanceof MSpeechPhrases)) {
            return false;
        }
        return id != null && id.equals(((MSpeechPhrases) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MSpeechPhrases{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", phrasesId='" + getPhrasesId() + "'" +
            ", delFlag=" + getDelFlag() +
            ", insOperCd='" + getInsOperCd() + "'" +
            ", insDateTime='" + getInsDateTime() + "'" +
            ", updOperCd='" + getUpdOperCd() + "'" +
            ", updDateTime='" + getUpdDateTime() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
