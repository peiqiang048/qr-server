package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QRコード印刷出力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

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
     * 接続名称.
     */
    private String connectionMethod;

    /**
     * プリンタブランドCD.
     */
    private String brandCode;

    /**
     * 伝票幅CD.
     */
    private String printSize;
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
    /**
     * テーブル名.
     */
    private String tableNameLabel;
    /**
     * テーブル名.
     */
    private String tableName;

}
