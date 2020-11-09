package com.cnc.qr.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * エラー情報保持クラス.
 */
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 結果コード.
     */
    private final String resultCode;

    /**
     * メッセージ.
     */
    private final String message;

    /**
     * 対象識別値.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;

    /**
     * 詳細情報リスト.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiError> details = new ArrayList<>();

    public ApiError(String resultCode, String message) {
        this(resultCode, message, null);
    }

    public ApiError(String resultCode, String message, String target) {
        this.resultCode = resultCode;
        this.message = message;
        this.target = target;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public String getTarget() {
        return target;
    }

    public List<ApiError> getDetails() {
        return details;
    }

    public void addDetail(ApiError detail) {
        details.add(detail);
    }
}
