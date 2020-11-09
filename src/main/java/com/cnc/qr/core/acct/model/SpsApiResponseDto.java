package com.cnc.qr.core.acct.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * SBペイメント返金結果情報.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sps-api-response")
public class SpsApiResponseDto {

    /**
     * 機能ID.
     */
    @XmlAttribute(name = "id")
    private String id;

    /**
     * 処理結果ステータス.
     */
    @XmlElement(name = "res_result")
    private String resResult;

    /**
     * 処理 SBPS トランザクション ID.
     */
    @XmlElement(name = "res_sps_transaction_id")
    private String resSpsTransactionId;

    /**
     * 処理完了日時.
     */
    @XmlElement(name = "res_process_date")
    private String resProcessDate;

    /**
     * エラーコード .
     */
    @XmlElement(name = "res_err_code")
    private String resErrCode;

    /**
     * レスポンス日時.
     */
    @XmlElement(name = "res_date")
    private String resDate;
}
