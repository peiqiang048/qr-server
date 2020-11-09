package com.cnc.qr.core.acct.model;

import lombok.Data;

/**
 * SBペイメントQRGateWay接続結果情報.
 */
@Data
public class SpsGateWayResultDto {

    /**
     * 接続結果.
     */
    private SpsResultDto result;

    /**
     * 処理結果コード.
     */
    private String resultCode;

    /**
     * 処理結果MSG.
     */
    private String resultMessage;

    /**
     * 処理結果タイプ.
     */
    private String resultType;
}
