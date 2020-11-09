package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリ伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonalDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 氏名.
     */
    private String customerName;
    /**
     * 電話番号.
     */
    private String telNumber;
    /**
     * 住所.
     */
    private String address;

    /**
     * 要望.
     */
    private String comment;


    /**
     * お客様情報.
     */
    private String personalInfoLabel;
    /**
     * 氏名.
     */
    private String customerNameLabel;
    /**
     * お客様住所.
     */
    private String personalAddressLabel;


    /**
     * お客様電話番号.
     */
    private String personalTelNumberLabel;

    /**
     * お客様要望.
     */
    private String personalCommentLabel;

    /**
     * 配送料金.
     */
    private String cateringChargeAmountLabel;

    /**
     * 配送料金.
     */
    private String cateringChargeAmount;

}
