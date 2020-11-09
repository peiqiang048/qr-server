package com.cnc.qr.common.constants;

/**
 * 正規表現用定数クラス.
 */
public final class RegexConstants {

    /**
     * ログインIDチェック.
     */
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    /**
     * 電話番号チェック.
     */
    public static final String MOBILE_VALIDATION = "^\\d{2,5}\\-\\d{1,4}\\-\\d{1,4}$";

    /**
     * 英数字チェック.
     */
    public static final String ALPHANUMERIC_VALIDATION = "^[0-9a-zA-Z]*$";

    /**
     * 数字チェック.
     */
    public static final String NUMERIC_VALIDATION = "^[0-9]*$";

    /**
     * 正の整数チェック.
     */
    public static final String POSITIVE_INTEGER = "^[1-9]\\d*$";

    /**
     * 携帯電話番号チェック.
     */
    public static final String TEL_VALIDATION = "^(090|080|070|060)\\-\\d{4}\\-\\d{4}$";

    /**
     * 郵便番号チェック.
     */
    public static final String POSTAL_VALIDATION = "^\\d{3}\\d{4}$";

    /**
     * 漢字チェック.
     */
    public static final String KANJI_VALIDATION = "([\\x{3005}\\x{3007}\\x{303b}\\x{3400}-\\x{9FFF}\\x{F900}-\\x{FAFF}\\x{20000}-\\x{2FFFF}])(.*|)/u";

    /**
     * 全角カタカナ チェック.
     */
    public static final String KATAKANA_VALIDATION = "^(?:\\xE3\\x82[\\xA1-\\xBF]|\\xE3\\x83[\\x80-\\xB6]|ー)+$";

    /**
     * ひらがな チェック.
     */
    public static final String HIRAKANA_VALIDATION = "^(?:\\xE3\\x81[\\x81-\\xBF]|\\xE3\\x82[\\x80-\\x93]|ー)+$";

    /**
     * クレジットカード 名義チェック（英字大文字）.
     */
    public static final String CREDITCARD_NAME_VALIDATION = "^[A-Z]+[\\s|　]+[A-Z]+[\\s|　]*[A-Z]+$";

    private RegexConstants() {
    }
}
