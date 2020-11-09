package com.cnc.qr.common.constants;

import com.alibaba.fastjson.JSONObject;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * コード定数クラス.
 */
public final class CodeConstants {

    /**
     * プリンターブランド名.
     */
    @Getter
    @AllArgsConstructor
    public enum PrinterBrand {
        STAR("01", "スター"),
        EPSON("02", "エピソン"),
        CHP("03", "中国製プリンター"),
        OTHER("04", "その他");

        private String code;
        private String value;
    }

    /**
     * 結果コード.
     */
    @Getter
    @AllArgsConstructor
    public enum ResultCode {
        SUCCESS("0000", "正常終了"),
        UNAUTHORIZED("1000", "ログインしていないまたトークンの有効期限が切れています"),
        FORBIDDEN("1001", "権限なし"),
        FAILED("9999", "システムエラーが発生しました"),
        VALIDATE_FAILED("9001", "パラメータチェック失敗");

        private String resultCode;
        private String message;
    }

    /**
     * 結果コード.
     */
    @Getter
    @AllArgsConstructor
    public enum SettleType {
        SETTLE("1", "精算"),
        INSPECTION("2", "点検");


        private String code;
        private String value;
    }

    /**
     * 店舗媒体种类.
     */
    @Getter
    @AllArgsConstructor
    public enum MediumType {
        IMAGE("01", "スター"),
        LOGO("02", "ロゴ");

        private String code;
        private String value;
    }

    /**
     * 店舗媒体用途区分.
     */
    @Getter
    @AllArgsConstructor
    public enum TerminalDistinction {
        CSMB("01", "客用手机"),
        CSPD("02", "客用PAD"),
        STMB("03", "店用手机"),
        STPD("04", "会计PAD"),
        ADMIN("05", "管理画面");

        private String code;
        private String value;
    }


    /**
     * 呼出対応状況.
     */
    @Getter
    @AllArgsConstructor
    public enum CallStatus {
        NOT_COMPATIBLE("01", "未対応"),
        DONE("02", "対応済");

        private String code;
        private String value;
    }

    /**
     * 税区分.
     */
    @Getter
    @AllArgsConstructor
    public enum TaxType {
        SOTO_RATE("1", "外税"),
        UCHI_RATE("2", "内税"),
        TAX_EXEMPT("3", "非課税");

        private String code;
        private String value;

        /**
         * 税区分取得.
         */
        public static String getTaxText(String taxCode) {
            for (TaxType taxType : TaxType.values()) {
                if (Objects.equals(taxType.getCode(), taxCode)) {
                    return taxType.getValue();
                }
            }
            return TaxType.SOTO_RATE.getValue();
        }
    }

    /**
     * 税端数処理区分.
     */
    @Getter
    @AllArgsConstructor
    public enum TaxRoundType {
        ROUNDING("1", "四捨五入"),
        ROUND_UP("2", "切り上げ"),
        ROUND_DOWN("3", "切り捨て");

        private String code;
        private String value;
    }

    /**
     * 税端数処理区分.
     */
    @Getter
    @AllArgsConstructor
    public enum TaxReliefApplyType {
        NOT_ALWAYS_APPLIED("0", "常時非適用"),
        ALWAYS_APPLIED("1", "常時適用"),
        SWITCHING_ALWAYS_APPLIED("2", "切替適用");

        private String code;
        private String value;
    }

    /**
     * テイクアウト区分.
     */
    @Getter
    @AllArgsConstructor
    public enum TakeoutFlag {
        EAT_IN("0", "イートイン"),
        TAKE_OUT("1", "テイクアウト");

        private String code;
        private String value;
    }

    /**
     * プリンター接続方式.
     */
    @Getter
    @AllArgsConstructor
    public enum PrinterConnectionMethod {
        WIFI("0", "Wi-Fi"),
        LAN("1", "ラン"),
        BLUETOOTH("2", "ブルートゥース");

        private String code;
        private String value;
    }

    /**
     * プリンター印紙幅.
     */
    @Getter
    @AllArgsConstructor
    public enum PrintSize {
        LONG("01", "58mm"),
        SHORT("02", "80mm");

        private String code;
        private String value;
    }

    /**
     * 表示ステータス.
     */
    @Getter
    @AllArgsConstructor
    public enum DisplayStatus {
        HIDDEN("0", "非表示"),
        DISPLAY("1", "表示");

        private String code;
        private String value;
    }


    /**
     * 支払方式.
     */
    @Getter
    @AllArgsConstructor
    public enum AccountsType {
        CASH("00", "現金"),
        PAYPAY("01", "PayPay"),
        LINEPAY("02", "LinePay"),
        WECHATPAY("03", "WeChatPay"),
        ALIPAY("04", "AliPay"),
        CREDIT("05", "Credit"),
        RAKUTEN("06", "Rakuten"),
        QR_ALIPAY("21", "Alipay"),
        QR_WECHATPAY("22", "WeChatPay"),
        QR_UNIONPAY("23", "銀聯QR"),
        QR_LINEPAY("24", "LinePay"),
        QR_ORIGAMI("27", "Origami"),
        QR_RAKUTENPAY("29", "Rakuten Pay"),
        QR_DPAY("31", "ｄ払い"),
        QR_PAYPAY("32", "Paypay"),
        QR_MERPAY("35", "メルペイ"),
        QR_AUPAY("36", "auPay"),
        CASH_PAYPAY("51", "Paypay"),
        CASH_CREDIT("52", "Credit"),
        QR_CODE_PAY("98", "QRコードPAY"),
        ANY("99", "任意");

        private String code;
        private String value;
    }

    /**
     * 売状態.
     */
    @Getter
    @AllArgsConstructor
    public enum SellOnOff {

        SELL_OFF("0", "非売品"),
        SELL_ON("1", "売品");

        private String code;
        private String value;
    }

    /**
     * 言語.
     */
    @Getter
    @AllArgsConstructor
    public enum Language {

        CN("01", "中国語"),
        JP("02", "日本語"),
        EN("03", "英語"),
        KO("04", "韓国語");

        private String code;
        private String value;
    }


    /**
     * 支払状態.
     */
    @Getter
    @AllArgsConstructor
    public enum PayStatus {
        PAY_ALREADY("01", "支払済"),
        PAY_NOT_ALREADY("02", "未支払");

        private String code;
        private String value;
    }

    /**
     * 注文状態.
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStatus {
        TENTATIVE_ORDER("01", "仮注文"),
        ORDER("02", "注文");

        private String code;
        private String value;
    }

    /**
     * 付随区分.
     */
    @Getter
    @AllArgsConstructor
    public enum Accompanying {
        RADIO("0", "ラジオ"),
        MULTIPLE("1", "複数選択"),
        TOPPING("2", "トッピング");

        private String code;
        private String value;
    }


    /**
     * 認証トークンチェック結果.
     */
    @AllArgsConstructor
    public enum AppTokenStatus {

        APP_ID_NO_FOUND(10001, "access_tokenを取得した場合、APPIDまたはAPPSecretは存在しません。"),
        APP_TOKEN_INVALID(10002, "不適法なaccess_tokenは有効性を確認してください。"),
        APP_TOKEN_CREATE_COUNT_EXCEED(10003, "access_tokenを取得すると、毎日の最大回数を超えます。"),
        APP_TOKEN_NULL(10004, "access_tokenパラメータが足りません。");

        private final int code;
        private final String reason;

        public int getStatusCode() {
            return code;
        }

        public String getReasonPhrase() {
            return reason;
        }

        /**
         * 認証トークンチェック結果.
         */
        public static String getReasonPhrase(int statusCode) {
            for (AppTokenStatus ps : AppTokenStatus.values()) {
                if (ps.getStatusCode() == statusCode) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("errorCode", ps.getStatusCode());
                    jsonObject.put("errorMsg", ps.getReasonPhrase());
                    return jsonObject.toString();
                }
            }
            return null;
        }
    }

    /**
     * 商品区分.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemType {
        NORMAL("0", "正常"),
        SPECIAL("1", "サービス"),
        RETURNS("2", "返品");

        private String code;
        private String value;
    }

    /**
     * 商品状態.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemStatus {
        UNCONFIRMED("01", "未確認"),
        CONFIRMED("02", "確認済");

        private String code;
        private String value;
    }

    /**
     * フラグ.
     */
    @Getter
    @AllArgsConstructor
    public enum Flag {
        ON(1, "TRUE"),
        OFF(0, "FALSE");

        private Integer code;
        private String value;
    }

    /**
     * オプション種類.
     */
    @Getter
    @AllArgsConstructor
    public enum OptionType {
        SINGLE_CHOICE("0", "単一選択"),
        MULTIPLE_SELECTION("1", "複数選択"),
        QUANTITY_SELECTION("2", "数量選択");

        private String code;
        private String value;
    }

    /**
     * 支払区分.
     */
    @Getter
    @AllArgsConstructor
    public enum PaymentType {
        ADVANCE_PAYMENT("01", "先払"),
        DEFERRED_PAYMENT("02", "後払");

        private String code;
        private String value;
    }

    /**
     * 注文表示状態.
     */
    @Getter
    @AllArgsConstructor
    public enum OrderShowStatus {
        UNCONFIRMED("01", "未確認"),
        PAY_NOT_ALREADY("02", "未支払"),
        PAY_ALREADY("03", "支払済");

        private String code;
        private String value;
    }

    /**
     * 会計方式.
     */
    @Getter
    @AllArgsConstructor
    public enum RegistPaymentType {
        KAIKEI("01", "会計"),
        SAIKAIKEI("02", "再会計");

        private String code;
        private String value;
    }

    /**
     * 停用标识.
     */
    @Getter
    @AllArgsConstructor
    public enum StopFlag {
        ACTIVATE(1, "アクティブ"),
        DEACTIVATE(0, "非アクティブ化");

        private Integer code;
        private String value;
    }

    /**
     * メニュー区分.
     */
    @Getter
    @AllArgsConstructor
    public enum MenuType {
        FRONT("01", "会計タブレット"),
        MANAGEMENT("02", "管理画面");

        private String code;
        private String value;
    }

    /**
     * ユーザ区分.
     */
    @Getter
    @AllArgsConstructor
    public enum UserType {
        FRONT("1", "フロントユーザ"),
        FRONTANDBACK("2", "フロント&バックユーザ"),
        BACK("3", "バックユーザ");

        private String code;
        private String value;
    }

    /**
     * 注文区分.
     */
    @Getter
    @AllArgsConstructor
    public enum OrderType {
        CALL("01", "コール"),
        MOBILE("02", "スマホ"),
        PAD("03", "会計PAD"),
        FRONT("04", "店員"),
        DELIVERY("05", "出前");

        private String code;
        private String value;
    }

    /**
     * 出前印刷種類.
     */
    @Getter
    @AllArgsConstructor
    public enum DeliverPrintType {
        KITCHEN("1", "キッチン印刷"),
        ACCOUNTING("2", "レシート印刷"),
        RECEIPT("3", "領収書");

        private String code;
        private String value;
    }

    /**
     * 注文表示状態.
     */
    @Getter
    @AllArgsConstructor
    public enum StaffCheckFlag {
        YES("1", "要"),
        NO("0", "不要");

        private String code;
        private String value;
    }

    /**
     * 支払結果.
     */
    @Getter
    @AllArgsConstructor
    public enum PayResult {
        DEFAULT("AA", "デフォルト"),
        SUCCESS("00", "成功"),
        FAILED("01", "失敗");

        private String code;
        private String value;
    }

    /**
     * サービスタイプ.
     */
    @Getter
    @AllArgsConstructor
    public enum ServiceType {
        PURCHASE("0", "売上（購入）"),
        CANCEL("1", "取消（月額課金解約）");

        private String code;
        private String value;
    }

    /**
     * 処理結果ステータス.
     */
    @Getter
    @AllArgsConstructor
    public enum ResResult {
        OK("OK", "要求処理 OK"),
        NG("NG", "要求 NG"),
        CR("CR", "課金解約された"),
        CC("CC", "キャリア解約"),
        PY("PY", "入金通知"),
        LN("LN", "期限切れキャンセル"),
        CL("CL", "最終課金月による解約");

        private String code;
        private String value;
    }

    /**
     * 会社.
     */
    @Getter
    @AllArgsConstructor
    public enum Company {
        TRIO("1", "TRIO"),
        SB("2", "SB");

        private String code;
        private String value;
    }

    /**
     * 会社決済手段.
     */
    @Getter
    @AllArgsConstructor
    public enum CompanyMethod {
        PAYMENT("1", "購入"),
        REFUNDS("2", "返金"),
        CPM_PAY("3", "決済要求"),
        CPM_PAY_INFO("4", "決済履歴検索"),
        CPM_REFUND("5", "返金要求"),
        CPM_REFUND_INFO("6", "返金履歴検索"),
        CPM_PAY_CANCEL("7", "決済取消");

        private String code;
        private String value;
    }

    /**
     * 返金要求機能ID.
     */
    @Getter
    @AllArgsConstructor
    public enum SbRefundsId {
        PAYPAY("01", "ST02-00306-311"), // PayPay（オンライン決済）：返金要求
        LINEPAY("02", "ST02-00202-310"), // LINE Pay：返金要求
        ALIPAY("04", "ST02-00306-510"), // Alipay 国際決済：返金要求
        CREDIT("05", "ST02-00303-101"), // クレジットカード決済：取消返金要求
        RAKUTEN("06", "ST02-00306-305"); // 楽天ペイ（オンライン決済）：取消返金要求

        private String code;
        private String value;

        /**
         * 返金要求機能ID取得.
         */
        public static String getSbRefundsId(String paymentCode) {
            for (SbRefundsId sr : SbRefundsId.values()) {
                if (Objects.equals(sr.getCode(), paymentCode)) {
                    return sr.getValue();
                }
            }
            return SbRefundsId.PAYPAY.getValue();
        }
    }

    /**
     * 読取方式.
     */
    @Getter
    @AllArgsConstructor
    public enum PayType {
        CPM("01", "CPM"),
        MPM("02", "MPM");

        private String code;
        private String value;
    }


    /**
     * 決済状態.
     */
    @Getter
    @AllArgsConstructor
    public enum SbOrderStatus {
        SUCCESS("1", "取引成功"),
        FAILURE("3", "取引失敗"),
        COMPLETION("4", "取引完結"),
        PAYING("7", "エンドユーザ支払い中");

        private String code;
        private String value;
    }

    /**
     * 注文商品区分.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemClassification {
        NORMAL("0", "正常"),
        SERVICE("1", "サービス"),
        RETURN("2", "返品"),
        SET("3", "セット");

        private String code;
        private String value;
    }

    /**
     * 商品区分.
     */
    @Getter
    @AllArgsConstructor
    public enum MstItemType {
        USUALLY("01", "普通"),
        SETMEAL("02", "定食"),
        COURSE("03", "コース"),
        BUFFET("04", "放題");

        private String code;
        private String value;
    }


    /**
     * 放題種類.
     */
    @Getter
    @AllArgsConstructor
    public enum BuffetType {
        SINGLE_CHOICE("0", "放題なし"),
        MULTIPLE_SELECTION("1", "放題のみ"),
        QUANTITY_SELECTION("2", "放題あり"),
        CATEGORY_SELECTION("3", "放題種類");

        private String code;
        private String value;
    }

    /**
     * 予約状態.
     */
    @Getter
    @AllArgsConstructor
    public enum ReservateStatus {
        RESERVATING("01", "予約中"),
        RESERVATED("02", "処理済"),
        CANCEL("03", "取消");

        private String code;
        private String value;
    }

    /**
     * 予約関連分類名.
     */
    @Getter
    @AllArgsConstructor
    public enum ReservateClassification {
        COURSE("01", "コース"),
        BUFFET("02", "放題"),
        TABLE("03", "席");

        private String code;
        private String value;
    }

    /**
     * 注文遷移.
     */
    @Getter
    @AllArgsConstructor
    public enum OrderTransfer {
        PROMPT("01", "提示"),
        ITEMLIST("02", "商品一覧"),
        BUFFETCCOURSELIST("03", "放題＆コース一覧");

        private String code;
        private String value;
    }

    /**
     * 商品状態.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemShowStatus {
        UNDERCARRIAGE("01", "下架"),
        OVERHEAD("02", "上架");

        private String code;
        private String value;
    }

    /**
     * 基本分類.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemCategory {
        COURSE(0, "コース"),
        BUFFET(-1, "放題");

        private Integer code;
        private String value;
    }

    /**
     * 役割固定.
     */
    @Getter
    @AllArgsConstructor
    public enum RoleId {
        BUSINESS(-1, "事業者"),
        SHOP_MANAGER(0, "店長");

        private Integer code;
        private String value;
    }

    /**
     * 出前状態.
     */
    @Getter
    @AllArgsConstructor
    public enum DeliveryStatus {
        PAID("1", "支払済"),
        ACCEPTED("2", "受付済"),
        INDELIVERY("3", "配達中"),
        PROCESSED("4", "処理済"),
        REFUND("5", "返金済");

        private String code;
        private String value;
    }

    /**
     * 出前仕方フラグ.
     */
    @Getter
    @AllArgsConstructor
    public enum DeliveryTypeFlag {
        CATERING("1", "配達"),
        TAKE_OUT("2", "持帰り");

        private String code;
        private String value;
    }

    /**
     * エリア区分.
     */
    @Getter
    @AllArgsConstructor
    public enum AreaType {
        PREFECTURE("01", "都道府県"),
        CITY("02", "市区町村");

        private String code;
        private String value;
    }

    /**
     * 印刷状態.
     */
    @Getter
    @AllArgsConstructor
    public enum PrintStatus {
        UNPRINT("01", "未印刷"),
        PRINTING("02", "印刷中"),
        PRINTED("03", "印刷済");

        private String code;
        private String value;
    }

    /**
     * 出前仕方フラグ.
     */
    @Getter
    @AllArgsConstructor
    public enum DeliveryTypeWay {
        CATERING("2", "配達・持帰り"),
        TAKEOUT("1", "持帰のみ"),
        NOWAY("0", "両方不可");

        private String code;
        private String value;
    }

    /**
     * 配達料区分.
     */
    @Getter
    @AllArgsConstructor
    public enum CateringChargeType {
        PERCENT("1", "パーセント"),
        AMOUNT("2", "金額");

        private String code;
        private String value;
    }

    /**
     * 商品区分.
     */
    @Getter
    @AllArgsConstructor
    public enum ItemBuffetType {
        ITEM("1", "商品"),
        BUFFETCOURSE("2", "放题·コース");

        private String code;
        private String value;
    }

    /**
     * 访问来源.
     */
    @Getter
    @AllArgsConstructor
    public enum UrlSource {
        TOP("1", "TOP"), // topの場合、トップ画面から進みます
        SingleOrder("2", "単品");// 単品の場合、単品ボタン押したら進みます

        private String code;
        private String value;
    }

    /**
     * メニュー種類.
     */
    @Getter
    @AllArgsConstructor
    public enum MenuShowType {
        SingleITEM("0", "単品"),
        BUFFETCOURSE("1", "放题·コース");

        private String code;
        private String value;
    }
}
