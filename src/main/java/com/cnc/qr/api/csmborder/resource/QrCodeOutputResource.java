package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * QRコード印刷出力情報.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class QrCodeOutputResource extends CommonOutputResource {

    /**
     * QRコードURL.
     */
    private String qrCodeUrl;

    /**
     * ブルートゥース名称.
     */
    private String bluetoothName;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * プリンタブランドCD.
     */
    private String brandCode;
    /**
     * 受付番号ラベル.
     */
    private String receptionNoLabel;
    /**
     * 受付番号.
     */
    private String receptionNo;
    /**
     * 受付時間ラベル.
     */
    private String receptionDateTimeLabel;
    /**
     * 受付時間.
     */
    private String receptionDateTime;
    /**
     * 担当ラベル.
     */
    private String staffNameLabel;
    /**
     * 担当.
     */
    private String staffName;
    /**
     * メッセージ.
     */
    private String message;

}
