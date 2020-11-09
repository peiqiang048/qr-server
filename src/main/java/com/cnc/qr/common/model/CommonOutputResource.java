package com.cnc.qr.common.model;

import com.cnc.qr.common.constants.CodeConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 共通アウトプットリソース.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonOutputResource {

    /**
     * 結果コード.
     */
    private String resultCode = CodeConstants.ResultCode.SUCCESS.getResultCode();

    /**
     * メッセージ.
     */
    private String message;

}
