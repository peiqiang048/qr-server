package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.EditStoreMediaDto;
import com.cnc.qr.core.pc.model.StoreMediaDto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStoreInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 店舗名.
     */
    @NotBlank
    private String storeName;

    /**
     * 店舗名カタカナ.
     */
    @NotBlank
    private String storeNameKatakana;

    /**
     * 店舗電話.
     */
    @NotBlank
    private String tel;

    /**
     * 郵便番号.
     */
    @NotBlank
    private String postNumber;

    /**
     * 店舗FAX.
     */
    @NotBlank
    private String fax;

    /**
     * 営業開始時間.
     */
    @NotBlank
    private String openStoreTime;

    /**
     * 営業締め時間.
     */
    @NotBlank
    private String closeStoreTime;
    
    /**
     * 最後注文時間.
     */
    @NotBlank
    private String orderEndTime;

    /**
     * 店舗紹介.
     */
    private String storeIntrodution;

    /**
     * 店舗住所.
     */
    @NotBlank
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
    private String defaultUseTime;

    /**
     * 店舗媒体.
     */
    private List<StoreMediaDto> delStoreMediaList;

}
