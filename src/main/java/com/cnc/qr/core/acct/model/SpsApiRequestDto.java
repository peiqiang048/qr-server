package com.cnc.qr.core.acct.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * SBペイメント返金情報.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sps-api-request")
public class SpsApiRequestDto {

    /**
     * 機能ID.
     */
    @XmlAttribute(name = "id")
    private String id;

    /**
     * マーチャントID.
     */
    @XmlElement(name = "merchant_id")
    private String merchantId;

    /**
     * サービスID.
     */
    @XmlElement(name = "service_id")
    private String serviceId;

    /**
     * 処理トラッキングID.
     */
    @XmlElement(name = "tracking_id")
    private String resTrackingId;

    /**
     * リクエスト日時 .
     */
    @XmlElement(name = "request_date")
    private String requestDate;

    /**
     * リクエスト許容時間.
     */
    @XmlElement(name = "limit_second")
    private String limitSecond;

    /**
     * チェックサム.
     */
    @XmlElement(name = "sps_hashcode")
    private String spsHashcode;
}
