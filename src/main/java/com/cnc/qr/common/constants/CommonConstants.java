package com.cnc.qr.common.constants;

/**
 * 共通定数クラス.
 */
public final class CommonConstants {

    /**
     * システムユーザーID.
     */
    public static final String SYSTEM_ACCOUNT = "system";

    /**
     * デフォルト言語.
     */
    public static final String DEFAULT_LANGUAGE = "ja_JP";

    /**
     * 匿名ユーザーID.
     */
    public static final String ANONYMOUS_USER = "anonymoususer";

    /**
     * コードグループ:言語.
     */
    public static final String CODE_GROUP_LANGUAGE = "0014";

    /**
     * コードグループ:支払方式.
     */
    public static final String CODE_GROUP_PAYMENT = "0009";

    /**
     * コードグループ:支払方式.
     */
    public static final String CODE_GROUP_PAY_LATER = "0021";

    /**
     * コードグループ:QRコード印刷言語.
     */
    public static final String CODE_GROUP_PRINT_QRCODE = "0035";

    /**
     * コードグループ:客用伝票印刷言語.
     */
    public static final String CODE_GROUP_PRINT_CUSTOMER = "0036";

    /**
     * コードグループ:キッチン用伝票印刷言語.
     */
    public static final String CODE_GROUP_PRINT_KITCHEN = "0037";

    /**
     * コードグループ:会計用伝票印刷言語.
     */
    public static final String CODE_GROUP_PRINT_ACCOUNT = "0038";

    /**
     * コードグループ:客用伝票印刷有無.
     */
    public static final String CODE_GROUP_PRINT_CUSTOMER_WITH_OR_OUT = "0039";

    /**
     * コードグループ:キッチン用伝票印刷(リスト)有無.
     */
    public static final String CODE_GROUP_PRINT_KITCHEN_LIST_WITH_OR_OUT = "0040";

    /**
     * コードグループ:キッチン用伝票印刷（明細）有無.
     */
    public static final String CODE_GROUP_PRINT_KITCHEN_DETAIL_WITH_OR_OUT = "0041";

    /**
     * コードグループ:会計用伝票印刷有無.
     */
    public static final String CODE_GROUP_PRINT_ACCOUNT_WITH_OR_OUT = "0042";

    /**
     * コードグループ:プリンターブランド.
     */
    public static final String CODE_GROUP_PRINT_BRAND = "0001";

    /**
     * コードグループ:プリンター接続方式.
     */
    public static final String CODE_GROUP_PRINTER_CONNECTION_METHOD = "0002";

    /**
     * コードグループ:プリンター印紙幅.
     */
    public static final String CODE_GROUP_PRINT_SIZE = "0003";

    /**
     * コードグループ:商品ステータス.
     */
    public static final String CODE_GROUP_ITEM_STATUS = "0011";

    /**
     * コードグループ:オプション種類区分.
     */
    public static final String CODE_GROUP_OPTION_TYPE_CLASSIFICATION = "0012";

    /**
     * コードグループ:店員確認.
     */
    public static final String CODE_GROUP_CONFIRM = "0010";

    /**
     * コードグループ:支払方式区分.
     */
    public static final String CODE_GROUP_PAYMENT_DIV = "0031";

    /**
     * コードグループ:媒体用途.
     */
    public static final String CODE_GROUP_MEDIA_USE = "0032";

    /**
     * コードグループ:トークン.
     */
    public static final String CODE_GROUP_TOKEN = "0033";

    /**
     * コードグループ:ユーザ区分.
     */
    public static final String CODE_GROUP_USER_CLASSIFICATION = "0034";

    /**
     * コードグループ:停用标识.
     */
    public static final String CODE_GROUP_STOP_FLAG = "0035";

    /**
     * コードグループ:テーブル区分.
     */
    public static final String CODE_GROUP_TABLE_TYPE = "0015";

    /**
     * コードグループ:利用時間.
     */
    public static final String CODE_GROUP_USE_TIME = "0016";

    /**
     * プリンターポーリング.
     */
    public static final String CONTROL_TYPE_POLL = "0034";


    /**
     * ライセンスマスタ 予約有無の種類CD.
     */
    public static final String RESERVATION_DISPLAY = "0015";

    /**
     * ライセンスマスタ 出前有無の種類CD.
     */
    public static final String DELIVERY_DISPLAY = "0016";

    /**
     * ライセンスマスタ 客用スマホ使用可能の種類CD.
     */
    public static final String SMART_PHONES_AVAILABLE = "0017";

    /**
     * ライセンスマスタ 音声注文使用可能の種類CD.
     */
    public static final String VOICE_ORDER_AVAILABLE = "0018";

    /**
     * 日付フォーマット:yyyy-MM-dd.
     */
    public static final String DATE_FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 日付フォーマット:yyyy-MM-dd HH:mm:ss.
     */
    public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日付フォーマット:yyyy-MM-dd HH:mm:ss.
     */
    public static final String DATE_FORMAT = "yyyy年MM月dd日";

    /**
     * 日付フォーマット:yyyy-MM-dd HH:mm:ss:SSS.
     */
    public static final String DATE_FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 日付フォーマット:yyyyMMddHHmmss.
     */
    public static final String DATE_FORMAT_DATE_TIMES = "yyyyMMddHHmmss";

    /**
     * 日付フォーマット:HH:mm:ss.
     */
    public static final String DATE_FORMAT_TIME = "HH:mm:ss";

    /**
     * 日付フォーマット:yyyyMMdd.
     */
    public static final String DATE_FORMAT_DATE_SHORT = "yyyyMMdd";

    /**
     * 日付フォーマット:yyyyMMddHHmmssSSS.
     */
    public static final String DATE_FORMAT_DATE_TIMESTAMP = "yyyyMMddHHmmssSSS";

    /**
     * タイムゾーン:東京.
     */
    public static final String TIMEZONE_TOKYO = "Asia/Tokyo";

    /**
     * APPパスワード.
     */
    public static final String DEFAULT_PASSWORD = "521@qr";

    /**
     * PCパスワード.
     */
    public static final String DEFAULT_PC_PASSWORD = "123456";

    /**
     * 登録更新者:スマホ.
     */
    public static final String OPER_CD_MOBILE = "APP_CUSTOMER_MOBILE";

    /**
     * 登録更新者:会計タブレット.
     */
    public static final String OPER_CD_STORE_PAD = "APP_STORE_PAD";

    /**
     * 登録更新者:PC.
     */
    public static final String OPER_CD_STORE_PC = "APP_STORE_PC";

    /**
     * 开始时间.
     */
    public static final String START_DATE_TIME = "00:00:00";

    /**
     * 结束时间.
     */
    public static final String END_DATE_TIME = "23:59:59";
    /**
     * 数字格式化.
     */
    public static final String DEFAULT_FORMAT = "#,###";

    /**
     * 待ち単位.
     */
    public static final String MIN = "min";

    /**
     * QRプリンター.
     */
    public static final Integer QR_PRINT = 1;

    /**
     * 客用伝票プリンター.
     */
    public static final Integer CUSTOMER_PRINT = 2;

    /**
     * キッチンリスト.
     */
    public static final Integer KITCHENLIST_PRINT = 3;

    /**
     * 会計プリンター.
     */
    public static final Integer ORDER_ACCOUNT_PRINT = 4;

    /**
     * 領収書プリンター.
     */
    public static final Integer RECEIPT_PRINT = 5;

    /**
     * 銭マーク.
     */
    public static final String MONEY_CHARACTER = "￥";

    /**
     * コードグループ:注文出前仕方フラグ.
     */
    public static final String CODE_GROUP_DELIVERY_TYPE_FLAG = "0036";

    /**
     * コードグループ:設定出前仕方フラグ.
     */
    public static final String CODE_GROUP_SET_DELIVERY_TYPE_FLAG = "0040";

    /**
     * コードグループ:出前状態.
     */
    public static final String CODE_GROUP_DELIVERY_STATUS = "0037";

    /**
     * コードグループ:出前区分.
     */
    public static final String CODE_GROUP_DELIVERY_FLAG = "0038";

    /**
     * コードグループ:配達料区分.
     */
    public static final String CODE_GROUP_CATERING_PRICE_TYPE = "0039";

    /**
     * 登録更新者:出前.
     */
    public static final String OPER_CD_DELIVERY = "APP_CUSTOMER_DELIVERY";

    /**
     * 定数：名詞.
     */
    public static final String SPEECH_NOUN = "名詞";

    /**
     * 定数：数詞.
     */
    public static final String SPEECH_NUMERAL = "数詞";

    /**
     * 定数：固有名詞.
     */
    public static final String SPEECH_PROPER_NOUN = "固有名詞";

    /**
     * 定数：普通名詞.
     */
    public static final String SPEECH_COMMON_NOUN = "普通名詞";

    /**
     * 定数：OR.
     */
    public static final String SPEECH_WHERE = " OR ";

    /**
     * 定数：OR.
     */
    public static final String SPEECH_OR_WHERE = "|";

    /**
     * 定数：数詞.
     */
    public static final String SPEECH_CHINESE_NUMERAL = "m";

    /**
     * 定数：普通名詞.
     */
    public static final String SPEECH_CHINESE_COMMON_NOUN = "n";

    private CommonConstants() {
    }
}
