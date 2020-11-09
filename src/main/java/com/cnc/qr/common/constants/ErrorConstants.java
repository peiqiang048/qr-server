package com.cnc.qr.common.constants;

import java.net.URI;

/**
 * 共通エラー定数クラス.
 */
public final class ErrorConstants {

    /**
     * エラー：同時実行失敗.
     */
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";

    /**
     * エラー：検証.
     */
    public static final String ERR_VALIDATION = "error.validation";

    /**
     * エラー対応URL.
     */
    public static final String PROBLEM_BASE_URL = "http://www.cncsys.co.jp/";

    /**
     * エラータイプ.
     */
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

    /**
     * エラータイプ :制約違反.
     */
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI
        .create(PROBLEM_BASE_URL + "/constraint-violation");

    /**
     * エラータイプ :無効なパスワード.
     */
    public static final URI INVALID_PASSWORD_TYPE = URI
        .create(PROBLEM_BASE_URL + "/invalid-password");

    /**
     * エラータイプ :メールはすでに使用されています.
     */
    public static final URI EMAIL_ALREADY_USED_TYPE = URI
        .create(PROBLEM_BASE_URL + "/email-already-used");

    /**
     * エラータイプ :ログインはすでに使用されています.
     */
    public static final URI LOGIN_ALREADY_USED_TYPE = URI
        .create(PROBLEM_BASE_URL + "/login-already-used");

    private ErrorConstants() {
    }
}
