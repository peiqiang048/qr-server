package com.cnc.qr.common.constants;

/**
 * URL定数クラス.
 */
public final class UrlConstants {

    /**
     * PC　ルートURL.
     */
    public static final String PC_ROOT = "/api/pc";

    /**
     * 客用スマホ　ルートURL.
     */
    public static final String CSMB_ROOT = "/api/csmb";

    /**
     * 認証トークン取得.
     */
    public static final String CSMB_GET_TOKEN = CSMB_ROOT + "/getToken";

    /**
     * トークン認証.
     */
    public static final String CSMB_TOKEN_VERIFICATION = CSMB_ROOT + "/tokenVerification";

    /**
     * 客用スマホ　店舗情報取得.
     */
    public static final String CSMB_GET_STORE_INFO = CSMB_ROOT + "/getStoreInfo";

    /**
     * 客用スマホ　店舗広告画像取得.
     */
    public static final String CSMB_GET_STORE_ADVERPIC = CSMB_ROOT + "/getStoreAdverPic";

    /**
     * 客用スマホ　言語順番取得.
     */
    public static final String CSMB_GET_ACTIVE_LANGUAGE = CSMB_ROOT + "/getActiveLanguage";

    /**
     * 客用スマホ　コール.
     */
    public static final String CSMB_CALL = CSMB_ROOT + "/call";


    /**
     * 客用スマホ　商品カテゴリー取得.
     */
    public static final String CSMB_GET_ITEM_CATEGORY = CSMB_ROOT + "/getItemCategory";

    /**
     * 客用スマホ　放題一覧取得.
     */
    public static final String CSMB_GET_BUFFET_LIST = CSMB_ROOT + "/getBuffetList";

    /**
     * 客用スマホ　放題明細情報取得.
     */
    public static final String CSMB_GET_BUFFET = CSMB_ROOT + "/getBuffet";

    /**
     * 客用スマホ　商品情報取得.
     */
    public static final String CSMB_GET_ITEM = CSMB_ROOT + "/getItems";

    /**
     * 客用スマホ　商品特別情報取得.
     */
    public static final String CSMB_GET_ITEM_OPTION_TYPE = CSMB_ROOT + "/getItemOptionType";

    /**
     * 客用スマホ　共有注文情報取得.
     */
    public static final String CSMB_GET_SHARE_ORDER = CSMB_ROOT + "/getShareOrderInfo";

    /**
     * 客用スマホ　注文情報取得.
     */
    public static final String CSMB_GET_ORDER = CSMB_ROOT + "/getOrderInfo";

    /**
     * 客用スマホ　注文.
     */
    public static final String CSMB_ORDER = CSMB_ROOT + "/order";

    /**
     * 客用スマホ　コース＆放题一览取得.
     */
    public static final String CSMB_COURSE_BUFFET_LIST = CSMB_ROOT + "/getCourseBuffetList";


    /**
     * 客用スマホ　税金额取得.
     */
    public static final String CSMB_GET_TAX_VALUE = CSMB_ROOT + "/getTaxValue";

    /**
     * 客用スマホ　税情报取得.
     */
    public static final String CSMB_GET_TAX_INFO = CSMB_ROOT + "/getTaxInfo";

    /**
     * 客用スマホ　店舗支払方式取得.
     */
    public static final String CSMB_GET_STORE_PAYMENT_INFO = CSMB_ROOT + "/getPaymentType";

    /**
     * 客用スマホ　支払いURL取得.
     */
    public static final String CSMB_GET_PAY_URL = CSMB_ROOT + "/getPayUrl";

    /**
     * 客用スマホ　遷移先判断.
     */
    public static final String CSMB_ORDER_TRANSFER = CSMB_ROOT + "/orderTransfer";

    /**
     * 客用スマホ　コース＆放题詳細.
     */
    public static final String CSMB_COURSE_BUFFET_DETIMAL = CSMB_ROOT + "/courseBuffetDetail";

    /**
     * 客用スマホ　コース＆放题注文確認取得.
     */
    public static final String CSMB_COURSE_BUFFET_CONFIRM = CSMB_ROOT + "/courseBuffetConfirm";

    /**
     * 客用スマホ　コース＆放题注文確定.
     */
    public static final String CSMB_COURSE_BUFFET_REGIST = CSMB_ROOT + "/courseBuffeRegist";

    /**
     * 客用スマホ　先払いBackUrl.
     */
    public static final String CSMB_WECHATALI_PAY_BACKURL = CSMB_ROOT + "/weChatAliPayBackUrl";

    /**
     * 客用スマホ　SBペイメント情报取得.
     */
    public static final String CSMB_SB_PAYMENT_INFO = CSMB_ROOT + "/getSbPaymentInfo";

    /**
     * 客用スマホ　SBペイメントコールバック.
     */
    public static final String CSMB_SB_PAYMENT_CALLBACK = CSMB_ROOT + "/sbPaymentCallBack";

    /**
     * 客用スマホ　テーブル情報.
     */
    public static final String CSMB_GET_TABLE_URL = CSMB_ROOT + "/getTable";

    /**
     * 客用スマホ　音声認識文字列取得.
     */
    public static final String CSMB_GET_TEXT_BY_SPEECH = CSMB_ROOT + "/getTextBySpeech";

    /**
     * 客用スマホ　商品情報取得.
     */
    public static final String CSMB_GET_ITEM_BY_SPEECH = CSMB_ROOT + "/getItemsBySpeech";
    
    /**
     * 客用スマホ　チェック注文商品.
     */
    public static final String CSMB_CHECK_ORDER_ITEM = CSMB_ROOT + "/checkOrderItem";

    /**
     * 店員スマホ　ルートURL.
     */
    public static final String STMB_ROOT = "/api/stmb";

    /**
     * 店員スマホ　注文確認未済商品クリア.
     */
    public static final String STMB_EMPTY_ITEM_URL = STMB_ROOT + "/emptyItem";

    /**
     * 店員スマホ 商品カテゴリー取得.
     */
    public static final String STMB_GET_CATEGORY_LIST = STMB_ROOT + "/getCategoryList";

    /**
     * 店員スマホ 商品取得.
     */
    public static final String STMB_GET_ITEM_LIST = STMB_ROOT + "/getItemList";

    /**
     * 店員スマホ 商品オプション取得.
     */
    public static final String STMB_GET_ITEM_OPTION_LIST = STMB_ROOT + "/getItemOptionList";

    /**
     * 店員スマホ エリアタイプ取得.
     */
    public static final String STMB_GET_AREA_TYPE_LIST = STMB_ROOT + "/getAreaTypeList";

    /**
     * 店員スマホ テーブル取得.
     */
    public static final String STMB_GET_TABLE_LIST = STMB_ROOT + "/getTableList";

    /**
     * 店員スマホ　注文確認未済商品削除.
     */
    public static final String STMB_DELETE_ITEM_URL = STMB_ROOT + "/deleteItem";

    /**
     * 店員スマホ　受付情報設定.
     */
    public static final String STMB_INIT_ORDER_URL = STMB_ROOT + "/initOrder";

    /**
     * 店員スマホ　ログイン.
     */
    public static final String STMB_LOGIN_URL = STMB_ROOT + "/login";

    /**
     * 店員スマホ　ユーザ一覧取得.
     */
    public static final String STMB_GET_USER_LIST_URL = STMB_ROOT + "/getUserList";

    /**
     * 店員スマホ　トップ画面情報取得.
     */
    public static final String STMB_HOME_PAGE_URL = STMB_ROOT + "/homePage";

    /**
     * 店員スマホ　注文一覧取得.
     */
    public static final String STMB_GET_ORDER_LIST_URL = STMB_ROOT + "/getOrderList";

    /**
     * 店員スマホ　注文未確認商品一覧取得.
     */
    public static final String STMB_GET_ORDER_ITEM_LIST_URL = STMB_ROOT + "/getOrderItemList";

    /**
     * 店員スマホ　パスワード登録.
     */
    public static final String STMB_REGIST_PASSWORD_URL = STMB_ROOT + "/registPassword";

    /**
     * 店員スマホ　注文履歴取得.
     */
    public static final String STMB_GET_ORDER_HISTORY_URL = STMB_ROOT + "/getOrderHistory";

    /**
     * 店員スマホ　注文状態確認.
     */
    public static final String STMB_SURE_ORDER_URL = STMB_ROOT + "/sureOrder";

    /**
     * 店員スマホ　注文.
     */
    public static final String STMB_REGIST_ORDER_URL = STMB_ROOT + "/registOrder";

    /**
     * 店員スマホ 後払い.
     */
    public static final String STMB_PAY_LATER = STMB_ROOT + "/payLater";

    /**
     * 店員スマホ　注文情報取得.
     */
    public static final String STMB_GET_ORDER_URL = STMB_ROOT + "/getOrder";

    /**
     * 店員スマホ　放題一覧取得.
     */
    public static final String STMB_GET_BUFFET_LIST = STMB_ROOT + "/getBuffetList";

    /**
     * 店員スマホ　放題明細情報取得.
     */
    public static final String STMB_GET_BUFFET = STMB_ROOT + "/getBuffet";

    /**
     * 会計タブレット　ルートURL.
     */
    public static final String STPD_ROOT = "/api/stpd";

    /**
     * 会計タブレット　ログイン.
     */
    public static final String STPD_LOGIN_URL = STPD_ROOT + "/login";

    /**
     * 会計タブレット　座席一覧取得.
     */
    public static final String STPD_GET_TABLE_LIST_URL = STPD_ROOT + "/getTableList";

    /**
     * 会計タブレット　飲食区分取得.
     */
    public static final String STPD_GET_TAKEOUT_FLAG_URL = STPD_ROOT + "/getTakeoutFlag";

    /**
     * 会計タブレット　会計画面初期化取得.
     */
    public static final String STPD_GET_ACCOUNT_INIT_URL = STPD_ROOT + "/getAccountInit";

    /**
     * 会計タブレット　注文商品情報取得.
     */
    public static final String STPD_GET_ITEM_URL = STPD_ROOT + "/getItem";

    /**
     * 会計タブレット　注文リスト取得.
     */
    public static final String STPD_GET_ORDER_LIST_URL = STPD_ROOT + "/getOrderList";

    /**
     * 会計タブレット　座席変更.
     */
    public static final String STPD_CHANGE_TABLE_URL = STPD_ROOT + "/changeTable";

    /**
     * 会計タブレット　商品カテゴリーリスト取得.
     */
    public static final String STPD_GET_CATEGORY_LIST_URL = STPD_ROOT + "/getCategoryList";

    /**
     * 会計タブレット　商品リスト取得.
     */
    public static final String STPD_GET_ITEM_LIST_URL = STPD_ROOT + "/getItemList";

    /**
     * 会計タブレット　放題一覧取得.
     */
    public static final String STPD_GET_BUFFET_LIST = STPD_ROOT + "/getBuffetList";

    /**
     * 会計タブレット　放題明細取得.
     */
    public static final String STPD_GET_BUFFET = STPD_ROOT + "/getBuffet";

    /**
     * 会計タブレット　返品原因リスト取得.
     */
    public static final String STPD_GET_RETURN_REASON_LIST_URL = STPD_ROOT + "/getReturnReasonList";

    /**
     * 会計タブレット　返品登録.
     */
    public static final String STPD_REGIST_RETURN_URL = STPD_ROOT + "/registReturn";

    /**
     * 会計タブレット　呼出中リスト取得.
     */
    public static final String STPD_CALL_LIST_URL = STPD_ROOT + "/getCallList";

    /**
     * 会計タブレット　人数変更.
     */
    public static final String STPD_CHANGE_CUSTOMER_COUNT_URL = STPD_ROOT + "/changeCustomerCount";

    /**
     * 会計タブレット　呼出状態変更.
     */
    public static final String STPD_MODIFY_CALL_STATUS_URL = STPD_ROOT + "/modifyCallStatus";
    /**
     * 会計タブレット　注文状態確認.
     */
    public static final String STPD_SURE_ORDER_ITEM_URL = STPD_ROOT + "/sureOrderItem";

    /**
     * 会計タブレット　ログイン初期化.
     */
    public static final String STPD_LOGIN_INI_URL = STPD_ROOT + "/getUserAndLanguageList";

    /**
     * 会計タブレット　注文.
     */
    public static final String STPD_REGIST_ORDER_URL = STPD_ROOT + "/registOrder";

    /**
     * 会計タブレット　配席.
     */
    public static final String STPD_SET_TABLE_URL = STPD_ROOT + "/setTable";

    /**
     * 会計タブレット　機能メニュー.
     */
    public static final String STPD_MENU_URL = STPD_ROOT + "/getMenuList";

    /**
     * 会計タブレット　席解除一覧.
     */
    public static final String STPD_SEAT_RELEASE_LIST_URL = STPD_ROOT + "/seatReleaseList";

    /**
     * 会計タブレット　席解除.
     */
    public static final String STPD_SEAT_RELEASE_URL = STPD_ROOT + "/seatRelease";

    /**
     * 会計タブレット　注文フラグ.
     */
    public static final String STPD_GET_ORDER_FLAG = STPD_ROOT + "/getOrderFlag";

    /**
     * 会計タブレット　注文廃棄.
     */
    public static final String STPD_DISCARD_ORDER = STPD_ROOT + "/discardOrder";

    /**
     * QRコード印刷.
     */
    public static final String PRINT_QR_CODE = CSMB_ROOT + "/getPrintQrCode";

    /**
     * 会計印刷.
     */
    public static final String PRINT_ORDER_ACCOUNT = CSMB_ROOT + "/getPrintOrderAccount";

    /**
     * 客用と厨房伝票.
     */
    public static final String PRINT_CUSTOMER_KITCHEN = CSMB_ROOT + "/getPrintCustomerKitchen";

    /**
     * 現金支払.
     */
    public static final String REGIST_PAYMENT = STPD_ROOT + "/registPayment";

    /**
     * 再支払.
     */
    public static final String AGAIN_REGIST_PAYMENT = STPD_ROOT + "/againRegistPayment";

    /**
     * 返金一覧.
     */
    public static final String REFUNDS_LIST = STPD_ROOT + "/getRefundsList";

    /**
     * 会計タブレット　注文商品リスト取得.
     */
    public static final String STPD_GET_ORDER_ITEM_LIST_URL = STPD_ROOT + "/getOrderItemList";

    /**
     * 会計タブレット　テーブルエリア.
     */
    public static final String STPD_GET_TABLE_AREA_TYPE_LIST_URL = STPD_ROOT + "/getAreaTypeList";

    /**
     * 会計タブレット　テーブル情報.
     */
    public static final String STPD_GET_TABLE_URL = STPD_ROOT + "/getTable";

    /**
     * 会計タブレット　QRコード発行.
     */
    public static final String STPD_QR_CODE_ISSUE_URL = STPD_ROOT + "/qrCodeIssue";

    /**
     * 会計タブレット　受付番号廃棄.
     */
    public static final String STPD_RECEPTION_DISPOSAL_URL = STPD_ROOT + "/receptionDisposal";

    /**
     * 会計タブレット　受付画面_（初期化&検索）&配席一覧画面_配席.
     */
    public static final String STPD_RECEPTION_INIT_URL = STPD_ROOT + "/getReceptionInit";

    /**
     * 会計タブレット　受付画面_配席一覧API.
     */
    public static final String STPD_ASSIGNATION_TABLE_URL = STPD_ROOT + "/assignationTable";

    /**
     * 会計タブレット　注文詳細API.
     */
    public static final String STPD_ORDER_DETAIL_URL = STPD_ROOT + "/getOrderDetail";

    /**
     * 会計タブレット　売上一覧API.
     */
    public static final String STPD_AMOUNT_SOLD_URL = STPD_ROOT + "/amountSold";

    /**
     * 会計タブレット　予約一覧取得API.
     */
    public static final String STPD_GET_RESERVATE_LIST_URL = STPD_ROOT + "/getReservateList";

    /**
     * 会計タブレット　取消API.
     */
    public static final String STPD_CANCEL_RESERVATE_URL = STPD_ROOT + "/cancelReservate";

    /**
     * 会計タブレット　来店API.
     */
    public static final String STPD_FINISH_RESERVATE_URL = STPD_ROOT + "/finishReservate";

    /**
     * 会計タブレット　予約情報取得API.
     */
    public static final String STPD_GET_RESERVATE_URL = STPD_ROOT + "/getReservate";

    /**
     * 会計タブレット　席一覧情報取得API.
     */
    public static final String STPD_ALL_TABLE_LIST_URL = STPD_ROOT + "/getAllTableList";

    /**
     * 会計タブレット　予約API.
     */
    public static final String STPD_REGIST_RESERVATE_URL = STPD_ROOT + "/registReservate";

    /**
     * 会計タブレット　注文一覧取得(出前).
     */
    public static final String STPD_GET_DELIVERY_ORDER_LIST_URL = STPD_ROOT + "/getDeliveryOrderList";

    /**
     * 会計タブレット　返金(出前).
     */
    public static final String STPD_REFUND_DELIVERY_ORDER_URL = STPD_ROOT + "/refundDeliveryOrder";

    /**
     * 会計タブレット　現金割勘.
     */
    public static final String STPD_REGIST_DUTCH_ACCOUNT_URL = STPD_ROOT + "/registDutchAccount";

    /**
     * 会計タブレット　QRコード後払割勘.
     */
    public static final String STPD_DUTCH_ACCOUNT_PAY_LATER_URL = STPD_ROOT + "/dutchAccountPayLater";

    /**
     * 会計タブレット　注文金額取得.
     */
    public static final String STPD_GET_TAX_AMOUNT_URL = STPD_ROOT + "/getTaxAmount";

    /**
     * 会計タブレット　注文印刷(出前).
     */
    public static final String STPD_PRINT_DELIVERY_ORDER_URL = STPD_ROOT + "/printDeliverOrder";

    /**
     * 会計タブレット　注文明細取得(出前).
     */
    public static final String STPD_GET_DELIVERY_ORDER_DETAIL_INFO_URL = STPD_ROOT + "/getDeliveryOrderDetailInfo";

    /**
     * 会計タブレット　注文編集取得(出前).
     */
    public static final String STPD_GET_DELIVERY_ORDER_EDIT_INFO_URL = STPD_ROOT + "/getDeliveryOrderEditInfo";

    /**
     * 会計タブレット　店舗指定区域取得(出前).
     */
    public static final String STPD_SELECTED_AREA_LIST_URL = STPD_ROOT + "/getSelectedAreaList";

    /**
     * 会計タブレット　注文編集(出前).
     */
    public static final String STPD_UPDATE_DELIVERY_ORDER_URL = STPD_ROOT + "/updateDeliveryOrder";

    /**
     * 会計タブレット　メール再送信(出前).
     */
    public static final String STPD_RESEND_MAIL_URL = STPD_ROOT + "/resendMail";

    /**
     * 会計タブレット　QRコード発行(出前).
     */
    public static final String STPD_QR_CODE_ISSUE_DELIVERY_URL = STPD_ROOT + "/qrCodeIssueDelivery";

    /**
     * 会計タブレット　印刷注文取得.
     */
    public static final String STPD_GET_PRINT_ORDER_LIST = STPD_ROOT + "/getPrintOrderList";

    /**
     * 会計タブレット　印刷状態変更.
     */
    public static final String STPD_CHANGE_PRINT_STATUS = STPD_ROOT + "/changePrintStatus";

    /**
     * 返金.
     */
    public static final String REFUNDS = STPD_ROOT + "/refunds";

    /**
     * 後払い.
     */
    public static final String PAY_LATER = STPD_ROOT + "/payLater";

    /**
     * 支払方式.
     */
    public static final String PAYMENT_TYPE_LIST_URL = STPD_ROOT + "/getPaymentTypeList";

    /**
     * 会計タブレット　店舗情報検証.
     */
    public static final String STPD_STORE_ID_VERIFICATION_URL = STPD_ROOT + "/storeIdVerification";

    /**
     * 会計タブレット　店舗情報検証.
     */
    public static final String STPD_STORE_DEVICE_REGIST_URL = STPD_ROOT + "/storeDeviceRegist";

    /**
     * 会計タブレット　領収書印刷.
     */
    public static final String STPD_STORE_RECEIPT_PRINT_URL = STPD_ROOT + "/receiptPrint";

    /**
     * 会計タブレット　会計印刷.
     */
    public static final String STPD_STORE_ACCOUNTINGPRINT_PRINT_URL = STPD_ROOT + "/accountingPrint";

    /**
     * 会計タブレット　キャッシュトーアオーペン.
     */
    public static final String STPD_STORE_OPEN_CASH_DOOR_URL = STPD_ROOT + "/openCashDoor";

    /**
     * 会計タブレット　点検精算初期化.
     */
    public static final String STPD_STORE_INSPECTION_SETTLE_INIT_URL = STPD_ROOT + "/getInspectionSettleInit";

    /**
     * 会計タブレット　点検精算.
     */
    public static final String STPD_STORE_INSPECTION_SETTLE_URL = STPD_ROOT + "/inspectionSettle";

    /**
     * PC　店舗一覧情報取得.
     */
    public static final String PC_GET_STORE_LIST_URL = PC_ROOT + "/getStoreList";

    /**
     * PC　店舗情報取得.
     */
    public static final String PC_GET_STORE_URL = PC_ROOT + "/getStore";

    /**
     * PC　店舗情報編集.
     */
    public static final String PC_CHANGE_STORE_URL = PC_ROOT + "/changeStore";

    /**
     * PC　キッチン一覧情報取得.
     */
    public static final String PC_GET_KITCHEN_LIST_URL = PC_ROOT + "/getKitchenList";

    /**
     * PC　キッチン情報取得.
     */
    public static final String PC_GET_KITCHEN_URL = PC_ROOT + "/getKitchen";

    /**
     * PC　キッチン編集.
     */
    public static final String PC_REGIST_KITCHEN_URL = PC_ROOT + "/registKitchen";

    /**
     * PC　キッチン情報削除.
     */
    public static final String PC_DELETE_KITCHEN_URL = PC_ROOT + "/deleteKitchen";

    /**
     * PC　プリンター一覧情報取得.
     */
    public static final String PC_GET_PRINT_LIST_URL = PC_ROOT + "/getPrintList";

    /**
     * PC　プリンター情報取得.
     */
    public static final String PC_GET_PRINT_URL = PC_ROOT + "/getPrint";

    /**
     * PC　プリンター編集.
     */
    public static final String PC_REGIST_PRINT_URL = PC_ROOT + "/registPrint";

    /**
     * PC　プリンター情報削除.
     */
    public static final String PC_DELETE_PRINT_URL = PC_ROOT + "/deletePrint";

    /**
     * PC　レシート一覧情報取得.
     */
    public static final String PC_GET_RECEIPT_LIST_URL = PC_ROOT + "/getReceiptList";

    /**
     * PC　レシート情報取得.
     */
    public static final String PC_GET_RECEIPT_URL = PC_ROOT + "/getReceipt";

    /**
     * PC　レシート編集.
     */
    public static final String PC_REGIST_RECEIPT_URL = PC_ROOT + "/registReceipt";

    /**
     * PC　商品情報取得.
     */
    public static final String PC_GET_ITEM_LIST = PC_ROOT + "/getItemList";

    /**
     * PC　コース情報取得.
     */
    public static final String PC_GET_COURSE_LIST = PC_ROOT + "/getCourseList";

    /**
     * PC　商品明細情報取得.
     */
    public static final String PC_GET_ITEM = PC_ROOT + "/getItem";

    /**
     * PC　コース明細情報取得.
     */
    public static final String PC_GET_COURSE = PC_ROOT + "/getCourse";

    /**
     * PC　コース編集.
     */
    public static final String PC_REGIST_COURSE_URL = PC_ROOT + "/registCourse";

    /**
     * PC　コース情報削除.
     */
    public static final String PC_DELETE_COURSE_URL = PC_ROOT + "/deleteCourse";

    /**
     * PC　コース商品ステータス更新.
     */
    public static final String PC_CHANGE_COURSE_STATUS = PC_ROOT + "/changeCourseStatus";

    /**
     * PC　コース商品順番編集.
     */
    public static final String PC_CHANGE_COURSE_SORT_ORDER = PC_ROOT + "/changeCourseSortOrder";

    /**
     * PC　カテゴリー情報取得.
     */
    public static final String PC_GET_CATEGORY_LIST = PC_ROOT + "/getCategoryList";

    /**
     * PC　カテゴリー明細情報取得.
     */
    public static final String PC_GET_CATEGORY = PC_ROOT + "/getCategory";

    /**
     * PC　商品カテゴリーリスト情報取得.
     */
    public static final String PC_GET_ITEM_CATEGORY = PC_ROOT + "/getItemCategory";

    /**
     * PC　カテゴリーの下に商品リスト情報取得.
     */
    public static final String PC_GET_CATEGORY_ITEM = PC_ROOT + "/getCategoryItem";

    /**
     * PC　商品情報削除.
     */
    public static final String PC_DELETE_ITEN = PC_ROOT + "/deleteItem";

    /**
     * PC　カテゴリー情報削除.
     */
    public static final String PC_DELETE_CATEGORY = PC_ROOT + "/deleteCategory";

    /**
     * PC　オプション種類情報取得.
     */
    public static final String PC_GET_OPTION_TYPE_LIST = PC_ROOT + "/getOptionTypeList";

    /**
     * PC　オプション種類明細情報取得.
     */
    public static final String PC_GET_OPTION_TYPE = PC_ROOT + "/getOptionType";

    /**
     * PC　オプション種類情報編集.
     */
    public static final String PC_REGIST_OPTION_TYPE = PC_ROOT + "/registOptionType";

    /**
     * PC　オプション種類情報削除.
     */
    public static final String PC_DELETE_OPTION_TYPE = PC_ROOT + "/deleteOptionType";

    /**
     * PC　オプション種類順番編集.
     */
    public static final String PC_CHANGE_OPTION_TYPE_SORT_ORDER = PC_ROOT + "/changeOptionTypeSortOrder";

    /**
     * PC　ログイン.
     */
    public static final String PC_LOGIN_URL = PC_ROOT + "/login";

    /**
     * PC　ホームページ情報取得.
     */
    public static final String PC_GET_HOME_PAGE_INFO_URL = PC_ROOT + "/getHomePageInfo";

    /**
     * PC　ユーザー一覧情報取得.
     */
    public static final String PC_GET_USER_LIST_URL = PC_ROOT + "/getUserList";

    /**
     * PC　ユーザ情報取得.
     */
    public static final String PC_GET_USER_URL = PC_ROOT + "/getUser";

    /**
     * PC　商品編集.
     */
    public static final String PC_REGIST_ITEM = PC_ROOT + "/registItem";

    /**
     * PC　カテゴリー編集.
     */
    public static final String PC_REGIST_CATEGORY = PC_ROOT + "/registCategory";

    /**
     * PC　商品順番編集.
     */
    public static final String PC_CHANGE_ITEM_SORT_ORDER = PC_ROOT + "/changeItemSortOrder";

    /**
     * PC　カテゴリー順番編集.
     */
    public static final String PC_CHANGE_CATEGORY_SORT_ORDER = PC_ROOT + "/changeCategorySortOrder";

    /**
     * PC　商品ステータス更新.
     */
    public static final String PC_CHANGE_ITEM_STATUS = PC_ROOT + "/changeItemStatus";

    /**
     * PC　ユーザ情報編集.
     */
    public static final String PC_REGIST_USER = PC_ROOT + "/registUser";

    /**
     * PC　ユーザ情報削除.
     */
    public static final String PC_DELETE_USER = PC_ROOT + "/deleteUser";

    /**
     * PC　ユーザパスワードリセット.
     */
    public static final String PC_RESET_USER_PASSWORD = PC_ROOT + "/resetUserPassword";

    /**
     * PC　ファイルアップロード.
     */
    public static final String PC_UPLOAD_FILE_URL = PC_ROOT + "/uploadFile";

    /**
     * 店舗change.
     */
    public static final String MT_CHANGE_STORE_URL = PC_ROOT + "/businessIdChangeStore";

    /**
     * PC　役割一覧情報取得.
     */
    public static final String PC_GET_ROLE_LIST_URL = PC_ROOT + "/getRoleList";

    /**
     * PC　役割情報取得.
     */
    public static final String PC_GET_ROLE_URL = PC_ROOT + "/getRole";

    /**
     * PC　役割情報編集.
     */
    public static final String PC_REGIST_ROLE = PC_ROOT + "/registRole";

    /**
     * PC　役割情報削除.
     */
    public static final String PC_DELETE_ROLE = PC_ROOT + "/deleteRole";

    /**
     * PC　役割ステータス更新.
     */
    public static final String PC_CHANGE_ROLE_STATUS = PC_ROOT + "/changeRoleStatus";

    /**
     * PC　ユーザパスワード更新.
     */
    public static final String PC_CHANGE_USER_PASSWORD = PC_ROOT + "/changeUserPassword";

    /**
     * PC　テーブル一覧情報取得.
     */
    public static final String PC_GET_TABLE_LIST_URL = PC_ROOT + "/getTableList";

    /**
     * PC　テーブル情報取得.
     */
    public static final String PC_GET_TABLE_URL = PC_ROOT + "/getTable";

    /**
     * PC　テーブル情報編集.
     */
    public static final String PC_REGIST_TABLE_URL = PC_ROOT + "/registTable";

    /**
     * PC　テーブル情報削除.
     */
    public static final String PC_DELETE_TABLE_URL = PC_ROOT + "/deleteTable";

    /**
     * PC　エリア一覧情報取得.
     */
    public static final String PC_GET_AREA_LIST_URL = PC_ROOT + "/getAreaList";

    /**
     * PC　エリア情報取得.
     */
    public static final String PC_GET_AREA_URL = PC_ROOT + "/getArea";

    /**
     * PC　エリア情報編集.
     */
    public static final String PC_REGIST_AREA_URL = PC_ROOT + "/registArea";

    /**
     * PC　エリア情報削除.
     */
    public static final String PC_DELETE_AREA_URL = PC_ROOT + "/deleteArea";

    /**
     * PC　オプション一覧情報取得.
     */
    public static final String PC_GET_OPTION_LIST_URL = PC_ROOT + "/getOptionList";

    /**
     * PC　オプション情報取得.
     */
    public static final String PC_GET_OPTION_URL = PC_ROOT + "/getOption";

    /**
     * PC　オプション情報編集.
     */
    public static final String PC_REGIST_OPTION_URL = PC_ROOT + "/registOption";

    /**
     * PC　オプション情報削除.
     */
    public static final String PC_DELETE_OPTION_URL = PC_ROOT + "/deleteOption";

    /**
     * PC　オプション順番情報編集.
     */
    public static final String PC_CHANGE_OPTION_SORT_ORDER_URL = PC_ROOT + "/changeOptionSortOrder";

    /**
     * PC　オプション順番一覧情報取得.
     */
    public static final String PC_GET_SAME_TYPE_OPTION_LIST_URL = PC_ROOT + "/getSameTypeOptionList";

    /**
     * PC　単位一覧情報取得.
     */
    public static final String PC_GET_UNIT_LIST_URL = PC_ROOT + "/getUnitList";

    /**
     * PC　単位情報取得.
     */
    public static final String PC_GET_UNIT_URL = PC_ROOT + "/getUnit";

    /**
     * PC　単位情報編集.
     */
    public static final String PC_REGIST_UNIT_URL = PC_ROOT + "/registUnit";

    /**
     * PC　単位情報削除.
     */
    public static final String PC_DELETE_UNIT_URL = PC_ROOT + "/deleteUnit";

    /**
     * PC　税設定一覧情報取得.
     */
    public static final String PC_GET_TAX_LIST_URL = PC_ROOT + "/getTaxList";

    /**
     * PC　税設定情報取得.
     */
    public static final String PC_GET_TAX_URL = PC_ROOT + "/getTax";

    /**
     * PC　税設定情報編集.
     */
    public static final String PC_REGIST_TAX_URL = PC_ROOT + "/registTax";

    /**
     * PC　税設定情報削除.
     */
    public static final String PC_DELETE_TAX_URL = PC_ROOT + "/deleteTax";

    /**
     * PC　ユーザアカウント情報.
     */
    public static final String PC_GET_ACCOUNT_URL = PC_ROOT + "/account";

    /**
     * PC　店舗情報取得.
     */
    public static final String PC_GET_STORE_INFO_URL = PC_ROOT + "/getStoreInfo";

    /**
     * PC　のみ放題一覧情報取得.
     */
    public static final String PC_GET_BUFFET_LIST_URL = PC_ROOT + "/getBuffetList";

    /**
     * PC　のみ放題情報取得.
     */
    public static final String PC_GET_BUFFET_URL = PC_ROOT + "/getBuffet";

    /**
     * PC　のみ放題編集.
     */
    public static final String PC_REGIST_BUFFET_URL = PC_ROOT + "/registBuffet";

    /**
     * PC　のみ放題情報削除.
     */
    public static final String PC_DELETE_BUFFET_URL = PC_ROOT + "/deleteBuffet";

    /**
     * PC　のみ放題商品ステータス更新.
     */
    public static final String PC_CHANGE_BUFFET_STATUS = PC_ROOT + "/changeBuffetStatus";

    /**
     * PC　のみ放題商品順番編集.
     */
    public static final String PC_CHANGE_BUFFET_SORT_ORDER = PC_ROOT + "/changeBuffetSortOrder";

    /**
     * PC　出前設定取得.
     */
    public static final String PC_GET_DELIVERY_SETTING = PC_ROOT + "/getDeliverySetting";

    /**
     * PC　指定区域取得.
     */
    public static final String PC_GET_SELECTED_AREA_LIST = PC_ROOT + "/getSelectedAreaList";

    /**
     * PC　出前設定情報更新.
     */
    public static final String PC_REGIST_DELIVER_SETTING = PC_ROOT + "/registDelivertSetting";

    /**
     * 出前スマホ　ルートURL.
     */
    public static final String CSDV_ROOT = "/api/csdv";

    /**
     * 出前　商品カテゴリー取得.
     */
    public static final String CSDV_GET_ITEM_CATEGORY = CSDV_ROOT + "/getItemCategory";

    /**
     * 出前　商品情報取得.
     */
    public static final String CSDV_GET_ITEM_LIST = CSDV_ROOT + "/getItemList";

    /**
     * 出前　商品特別情報取得.
     */
    public static final String CSDV_GET_ITEM_OPTION_TYPE = CSDV_ROOT + "/getItemOptionType";

    /**
     * 出前　注文情報取得.
     */
    public static final String CSDV_GET_DELIVERY_ORDER_INFO = CSDV_ROOT + "/getDeliveryOrderInfo";

    /**
     * 出前　注文.
     */
    public static final String CSDV_REGIST_DELIVERY_ORDER = CSDV_ROOT + "/registDeliveryOrder";

    /**
     * 出前　支払いURL取得.
     */
    public static final String CSDV_GET_PAY_URL = CSDV_ROOT + "/getPayUrl";

    /**
     * 出前　先払いBackUrl.
     */
    public static final String CSDV_WECHATALI_PAY_BACKURL = CSDV_ROOT + "/weChatAliPayBackUrl";

    /**
     * 出前　SBペイメント情报取得.
     */
    public static final String CSDV_SB_PAYMENT_INFO = CSDV_ROOT + "/getSbPaymentInfo";

    /**
     * 出前　SBペイメントコールバック.
     */
    public static final String CSDV_SB_PAYMENT_CALLBACK = CSDV_ROOT + "/sbPaymentCallBack";

    /**
     * 出前　店舗情報取得.
     */
    public static final String CSDV_GET_STORE_INFO = CSDV_ROOT + "/getStoreInfo";

    /**
     * 出前　店舗広告画像取得.
     */
    public static final String CSDV_GET_STORE_ADVERPIC = CSDV_ROOT + "/getStoreAdverPic";

    /**
     * 出前　言語順番取得.
     */
    public static final String CSDV_GET_ACTIVE_LANGUAGE = CSDV_ROOT + "/getActiveLanguage";

    /**
     * 出前　店舗支払方式取得.
     */
    public static final String CSDV_GET_STORE_PAYMENT_INFO = CSDV_ROOT + "/getPaymentType";

    /**
     * 出前　テーブル情報.
     */
    public static final String CSDV_GET_TABLE_URL = CSDV_ROOT + "/getTable";

    /**
     * 出前　税金额取得.
     */
    public static final String CSDV_GET_TAX_VALUE = CSDV_ROOT + "/getTaxValue";

    /**
     * 出前　税情报取得.
     */
    public static final String CSDV_GET_TAX_INFO = CSDV_ROOT + "/getTaxInfo";

    /**
     * 出前　出前仕方フラグ取得.
     */
    public static final String CSDV_GET_DELIVERY_TYPE_FLAG = CSDV_ROOT + "/getDeliveryTypeFlag";

    /**
     * 出前　注文編集取得.
     */
    public static final String CSDV_GET_DELIVERY_ORDER_EDIT_INFO = CSDV_ROOT + "/getDeliveryOrderEditInfo";

    /**
     * 出前　店舗指定区域取得.
     */
    public static final String CSDV_GET_SELECTDE_AREA_LIST = CSDV_ROOT + "/getSelectedAreaList";


    private UrlConstants() {
    }
}
