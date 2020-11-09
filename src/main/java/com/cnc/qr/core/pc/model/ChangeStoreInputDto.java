package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 店舗保存インプット.
 */
@Data
public class ChangeStoreInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗名カタカナ.
     */
    private String storeNameKatakana;

    /**
     * 店舗電話.
     */
    private String tel;

    /**
     * 郵便番号.
     */
    private String postNumber;

    /**
     * 店舗FAX.
     */
    private String fax;

    /**
     * 営業開始時間.
     */
    private String openStoreTime;

    /**
     * 営業締め時間.
     */
    private String closeStoreTime;
    
    /**
     * 最後注文時間.
     */
    private String orderEndTime;

    /**
     * 店舗紹介.
     */
    private String storeIntrodution;

    /**
     * 店舗住所.
     */
    private String storeAddress;

    /**
     * 店舗媒体.
     */
    private List<EditStoreMediaDto> storeMediaList;

    /**
     * 支払方式.
     */
    private String paymentTerminal;

    /**
     * スマホ注文確認要フラグ.
     */
    private String staffCheckFlag;
    
    /**
     * コース＆放題確認要フラグ.
     */
    private String courseBuffetCheck;

    /**
     * ディフォルト利用時間.
     */
    private BigDecimal defaultUseTime;

    /**
     * 店舗媒体.
     */
    private List<StoreMediaDto> delStoreMediaList;

}
