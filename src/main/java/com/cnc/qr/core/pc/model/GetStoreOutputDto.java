package com.cnc.qr.core.pc.model;

import com.cnc.qr.core.order.model.PaymentMethodDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 店舗編集取得検索結果.
 */
@Data
public class GetStoreOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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
     * 出前最後配達時間 .
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
     * 店舗メディア.
     */
    private List<StoreMediaDto> storeMediaList;

    /**
     * メディア用途.
     */
    private List<MediaUseDto> useList;

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
     * 支払方式リスト.
     */
    private List<PaymentMethodDto> paymentTypeList;

    /**
     * スマホ注文確認要否リスト.
     */
    private List<PaymentMethodDto> staffCheckList;
}
