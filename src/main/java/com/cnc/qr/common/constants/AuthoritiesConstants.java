package com.cnc.qr.common.constants;

/**
 * スプリングセキュリティ定数クラス.
 */
public final class AuthoritiesConstants {

    /**
     * ADMIN 権限.
     */
    public static final String ADMIN = "ROLE_ADMIN";

    /**
     * 普通ユーザ 権限.
     */
    public static final String USER = "ROLE_USER";

    /**
     * 匿名ユーザ 権限.
     */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 客用スマホ 権限.
     */
    public static final String MOBILE = "ROLE_1";

    /**
     * 注文権限.
     */
    public static final String ORDER = "ROLE_2";

    /**
     * 会計権限.
     */
    public static final String ACCOUNT = "ROLE_3";

    /**
     * 注文取消権限.
     */
    public static final String ORDER_CANCEL = "ROLE_4";

    /**
     * 商品メンテナンス権限.
     */
    public static final String ITEM_MAINTENANCE = "ROLE_5";

    /**
     * 店舗権限.
     */
    public static final String STORE_MAINTENANCE = "ROLE_6";

    /**
     * ユーザ権限.
     */
    public static final String USER_MAINTENANCE = "ROLE_7";

    /**
     * 日報権限.
     */
    public static final String DAILY_REPORT = "ROLE_8";

    /**
     * 月報権限.
     */
    public static final String MONTHLY_REPORT = "ROLE_9";

    /**
     * 店員スマホ注文権限.
     */
    public static final String STMB_ORDER = "ROLE_10";

    /**
     * 店員スマホ会計権限.
     */
    public static final String STMB_ACCOUNT = "ROLE_11";

    /**
     * 点検精算権限.
     */
    public static final String INSPECTION_SETTLE_REPORT = "ROLE_14";

    private AuthoritiesConstants() {
    }
}
