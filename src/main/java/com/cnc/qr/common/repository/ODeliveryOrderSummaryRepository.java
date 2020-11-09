package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.ODeliveryOrderSummary;
import com.cnc.qr.common.shared.model.SlipDeliveryDto;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 出前注文サマリテーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface ODeliveryOrderSummaryRepository extends
    JpaRepository<ODeliveryOrderSummary, Long> {

    /**
     * 出前注文一覧情報取得.
     *
     * @param storeId                   店舗ID
     * @param deliveryTypeFlagCodeGroup 注文出前仕方フラグ
     * @param statusCodeGroup           出前状態
     * @param deliveryTypeFlag          出前仕方
     * @param status                    状態
     * @param deliveryOrderTimeFrom     開始日付
     * @param deliveryOrderTimeTo       終了日付
     * @param pageable                  ページ情報
     * @return 出前注文一覧情報
     */
    @Query(value =
        "SELECT                                                                     "
            + "    t1.order_summary_id AS orderSummaryId,                                 "
            + "    t2.receivables_id AS receivablesId,                                    "
            + "    t5.order_id AS orderId,                                                "
            + "    t6.payment_method_code AS paymentCode,                                 "
            + "    TO_CHAR(t1.ins_date_time, 'YYYY-MM-DD HH24:MI:SS') AS orderTime,       "
            + "    t2.reception_no AS receivablesNo,                                        "
            + "    t3.code_name AS deliveryTypeFlag,                                      "
            + "    t1.payment_amount AS paymentAmount,                                    "
            + "    t1.status AS statusCd,                                                 "
            + "    t4.code_name AS statusName                                             "
            + "FROM o_delivery_order_summary t1                                           "
            + "INNER JOIN o_receivables t2                                                "
            + "ON t2.store_id = t1.store_id                                               "
            + "AND t2.receivables_id = t1.receivables_id                                  "
            + "AND t2.del_flag = 0                                                        "
            + "INNER JOIN o_order t5                                                      "
            + "ON t5.store_id = t1.store_id                                               "
            + "AND t5.order_summary_id = t1.order_summary_id                              "
            + "AND t5.del_flag = 0                                                        "
            + "INNER JOIN p_payment_detail t6                                             "
            + "ON t6.store_id = t1.store_id                                               "
            + "AND t6.order_summary_id = t1.order_summary_id                              "
            + "AND t6.del_flag = 0                                                        "
            + "INNER JOIN m_code t3                                                       "
            + "ON t3.code_group = :deliveryTypeFlagCodeGroup                              "
            + "AND t3.code = t1.delivery_type_flag                                        "
            + "AND t3.store_id = t1.store_id                                              "
            + "AND t3.del_flag = 0                                                        "
            + "INNER JOIN m_code t4                                                       "
            + "ON t4.code_group = :statusCodeGroup                                        "
            + "AND t4.code = t1.status                                                    "
            + "AND t4.store_id = t1.store_id                                              "
            + "AND t4.del_flag = 0                                                        "
            + "WHERE t1.store_id = :storeId                                               "
            + "AND (TO_TIMESTAMP(:deliveryOrderTimeFrom, 'YYYY-MM-DD HH24:MI:SS') <=      "
            + "t1.ins_date_time OR :deliveryOrderTimeFrom = '')                           "
            + "AND (TO_TIMESTAMP(:deliveryOrderTimeTo, 'YYYY-MM-DD HH24:MI:SS') >=        "
            + "t1.ins_date_time OR :deliveryOrderTimeTo = '')                             "
            + "AND (t1.delivery_type_flag = :deliveryTypeFlag OR :deliveryTypeFlag = '')  "
            + "AND (t1.status = :status OR :status = '')                                  "
            + "AND t1.del_flag = 0                                                        "
            + "AND t5.pay_status = '01'                                                   "
            + "ORDER BY t1.status ASC, t1.ins_date_time DESC, t1.order_summary_id ASC ", nativeQuery = true)
    Page<Map<String, Object>> findDeliveryOrderInfoByStoreId(
        @Param("storeId") String storeId,
        @Param("deliveryTypeFlagCodeGroup") String deliveryTypeFlagCodeGroup,
        @Param("statusCodeGroup") String statusCodeGroup,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("status") String status,
        @Param("deliveryOrderTimeFrom") String deliveryOrderTimeFrom,
        @Param("deliveryOrderTimeTo") String deliveryOrderTimeTo, Pageable pageable);

    /**
     * 出前注文詳細情報取得.
     *
     * @param storeId                   店舗ID
     * @param deliveryTypeFlagCodeGroup 注文出前仕方フラグ
     * @param statusCodeGroup           出前状態
     * @param receivablesId             受付ID
     * @param languages                 言語
     * @return 出前注文詳細情報
     */
    @Query(value = "SELECT TO_CHAR(t2.reception_time, 'YYYY-MM-DD HH24:MI:SS') AS receptionTime,  "
        + "t2.reception_no AS receptionNo,                                                    "
        + "t1.receivables_id AS receivablesId,                                                "
        + "t1.order_amount AS orderAmount,                                                    "
        + "t1.payment_amount AS paymentAmount,                                                "
        + "t1.customer_name AS customerName,                                                  "
        + "t1.tel_number AS telNumber,                                                        "
        + "TO_CHAR(t1.start_time, 'HH24:MI') ||                                               "
        + " '～' || TO_CHAR(t1.end_time, 'HH24:MI') AS deliveryTime,                          "
        + "t1.mail_address AS mailAddress,                                                    "
        + "(t5.prefecture_name ->> :languages) ||                                             "
        + "(t5.city_name ->> :languages) ||                                                   "
        + "(t5.block_name ->> :languages) ||                                                  "
        + " (t1.delivery_other) AS address,                                                   "
        + "t4.code_name AS status,                                                            "
        + "t3.code_name AS deliveryTypeFlag,                                                  "
        + "t1.comment AS comment,                                                             "
        + "t1.delivery_type_flag AS deliveryType,                                             "
        + "t1.catering_charge_amount AS cateringCharge                                        "
        + "FROM o_delivery_order_summary t1                                                   "
        + "INNER JOIN o_receivables t2                                                        "
        + "ON t2.store_id = t1.store_id                                                       "
        + "AND t2.receivables_id = t1.receivables_id                                          "
        + "AND t2.del_flag = 0                                                                "
        + "INNER JOIN m_code t3                                                               "
        + "ON t3.code_group = :deliveryTypeFlagCodeGroup                                      "
        + "AND t3.code = t1.delivery_type_flag                                                "
        + "AND t3.store_id = t1.store_id                                                      "
        + "AND t3.del_flag = 0                                                                "
        + "INNER JOIN m_code t4                                                               "
        + "ON t4.code_group = :statusCodeGroup                                                "
        + "AND t4.code = t1.status                                                            "
        + "AND t4.store_id = t1.store_id                                                      "
        + "AND t4.del_flag = 0                                                                "
        + "LEFT JOIN m_area t5                                                               "
        + "ON t5.prefecture_id = t1.prefecture_id                                             "
        + "AND t5.city_id = t1.city_id                                                        "
        + "AND t5.block_id = t1.block_id                                                      "
        + "AND t5.del_flag = 0                                                                "
        + "WHERE t1.store_id = :storeId                                                       "
        + "AND t1.receivables_id = :receivablesId                                             "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findDeliveryOrderDetailInfoByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("deliveryTypeFlagCodeGroup") String deliveryTypeFlagCodeGroup,
        @Param("statusCodeGroup") String statusCodeGroup,
        @Param("languages") String languages);

    /**
     * 出前注文編集情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 出前注文編集情報
     */
    @Query(value = "SELECT t1.customer_name AS customerName,                                      "
        + "t1.tel_number AS telNumber,                                                        "
        + "TO_CHAR(t1.start_time, 'HH24:MI') ||                                               "
        + " '～' || TO_CHAR(t1.end_time, 'HH24:MI') AS deliveryTime,                          "
        + "t1.mail_address AS mailAddress,                                                    "
        + "t1.prefecture_id AS prefectureId,                                                  "
        + "t1.city_id AS cityId,                                                              "
        + "t1.block_id AS blockid,                                                            "
        + "t1.delivery_other AS other,                                                        "
        + "t1.status AS status,                                                               "
        + "t1.delivery_type_flag AS deliveryTypeFlag,                                         "
        + "t1.comment AS comment                                                              "
        + "FROM o_delivery_order_summary t1                                                   "
        + "WHERE t1.store_id = :storeId                                                       "
        + "AND t1.order_summary_id = :orderSummaryId                                          "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findDeliveryOrderEditInfoByReceivablesId(
        @Param("storeId") String storeId, @Param("orderSummaryId") String orderSummaryId);

    /**
     * 出前注文商品リスト取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param languages     言語
     * @return 出前注文商品リスト
     */
    @Query(value = "SELECT t1.order_amount AS orderAmount,                 "
        + "t3.order_id AS orderId,                                         "
        + "t3.order_detail_id AS orderDetailId,                            "
        + "t3.item_id AS itemId,                                           "
        + "t5.item_name ->> :languages AS itemName,                        "
        + "t3.item_price AS itemPrice,                                     "
        + "t3.item_count AS itemCount,                                     "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount,                        "
        + "t6.option_name ->> :languages AS optionName,                    "
        + "t7.classification AS classification,                            "
        + "t3.item_classification AS itemClassification,                   "
        + "t3.item_status AS itemSureStatus                                "
        + "FROM o_delivery_order_summary t1                                "
        + "LEFT JOIN o_order t2                                            "
        + "ON t1.store_id = t2.store_id                                    "
        + "AND t1.order_summary_id = t2.order_summary_id                   "
        + "AND t2.del_flag = 0                                             "
        + "LEFT JOIN o_order_details t3                                    "
        + "ON t2.store_id = t3.store_id                                    "
        + "AND t2.order_id = t3.order_id                                   "
        + "AND t3.del_flag = 0                                             "
        + "LEFT JOIN o_order_details_option t4                             "
        + "ON t3.store_id = t4.store_id                                    "
        + "AND (t3.order_detail_id = t4.order_detail_id                    "
        + "  OR t3.return_order_detail_id = t4.order_detail_id)            "
        + "AND t4.del_flag = 0                                             "
        + "LEFT JOIN m_item t5                                             "
        + "ON t3.store_id = t5.store_id                                    "
        + "AND t3.item_id = t5.item_id                                     "
        + "AND t5.del_flag = 0                                             "
        + "LEFT JOIN m_option t6                                           "
        + "ON t4.store_id = t6.store_id                                    "
        + "AND t4.item_option_type_code = t6.option_type_cd                "
        + "AND t4.item_option_code = t6.option_code                        "
        + "AND t6.del_flag = 0                                             "
        + "LEFT JOIN m_option_type t7                                      "
        + "ON t6.store_id = t7.store_id                                    "
        + "AND t6.option_type_cd = t7.option_type_cd                       "
        + "AND t7.del_flag = 0                                             "
        + "WHERE t1.store_id = :storeId                                    "
        + "AND t1.receivables_id = :receivablesId                          "
        + "AND t1.del_flag = 0                                             "
        + "ORDER BY orderId ASC, orderDetailId ASC", nativeQuery = true)
    List<Map<String, Object>> findDeliveryOrderItemListByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages);

    /**
     * 出前支払方式取得.
     *
     * @param storeId                   店舗ID
     * @param codeGroupPaymentCodeGroup 支払方式
     * @param receivablesId             受付ID
     * @return 支払方式情報
     */
    @Query(value = "SELECT DISTINCT t3.code_name AS paymentMethod  "
        + "FROM o_delivery_order_summary t1                        "
        + "INNER JOIN p_payment_detail t2                          "
        + "ON t2.store_id = t1.store_id                            "
        + "AND t2.order_summary_id = t1.order_summary_id           "
        + "AND t2.del_flag = 0                                     "
        + "INNER JOIN m_code t3                                    "
        + "ON t3.code_group = :codeGroupPaymentCodeGroup           "
        + "AND t3.code = t2.payment_method_code                    "
        + "AND t3.store_id = t2.store_id                           "
        + "AND t3.del_flag = 0                                     "
        + "WHERE t1.store_id = :storeId                            "
        + "AND t1.receivables_id = :receivablesId                  "
        + "AND t1.del_flag = 0                                     "
        + "GROUP BY t3.code_name ", nativeQuery = true)
    String findDeliveryPaymentMethodByOrderSummaryId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("codeGroupPaymentCodeGroup") String codeGroupPaymentCodeGroup);

    /**
     * 出前注文サマリーデータロック.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param delFlag        削除フラグ
     * @return 注文サマリーデータ
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    ODeliveryOrderSummary findByStoreIdAndOrderSummaryIdAndDelFlag(String storeId,
        String orderSummaryId,
        Integer delFlag);

    /**
     * 出前注文サマリ更新処理.
     *
     * @param storeId          店舗ID
     * @param orderSummaryId   注文サマリID
     * @param deliveryTypeFlag 出前仕方フラグ
     * @param status           状態
     * @param startTime        出前開始時間
     * @param endTime          出前終了時間
     * @param customerName     氏名
     * @param telNumber        電話番号
     * @param prefectureId     都道府県ID
     * @param cityId           市区町村ID
     * @param blockId          町域番地ID
     * @param deliveryOther    住所
     * @param mailAddress      メール
     * @param comment          要望
     * @param updOperCd        更新者
     * @param updDateTime      更新日時
     */
    @Modifying
    @Query(value = "update o_delivery_order_summary    "
        + "set delivery_type_flag = :deliveryTypeFlag, "
        + "status = :status,                           "
        + "start_time = :startTime,                    "
        + "end_time = :endTime,                        "
        + "customer_name = :customerName,              "
        + "tel_number = :telNumber,                    "
        + "prefecture_id = :prefectureId,              "
        + "city_id = :cityId,                          "
        + "block_id = :blockId,                        "
        + "delivery_other = :deliveryOther,            "
        + "mail_address = :mailAddress,                "
        + "comment = :comment,                         "
        + "upd_oper_cd = :updOperCd,                   "
        + "upd_date_time = :updDateTime,               "
        + "version = version + 1                       "
        + "where store_id = :storeId                   "
        + "and order_summary_id = :orderSummaryId      "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateDeliveryOrder(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("status") String status,
        @Param("startTime") ZonedDateTime startTime,
        @Param("endTime") ZonedDateTime endTime,
        @Param("customerName") String customerName,
        @Param("telNumber") String telNumber,
        @Param("prefectureId") String prefectureId,
        @Param("cityId") String cityId,
        @Param("blockId") String blockId,
        @Param("deliveryOther") String deliveryOther,
        @Param("mailAddress") String mailAddress,
        @Param("comment") String comment,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 出前注文サマリ更新処理.
     *
     * @param storeId          店舗ID
     * @param orderSummaryId   注文サマリID
     * @param deliveryTypeFlag 出前仕方フラグ
     * @param status           状態
     * @param customerName     氏名
     * @param telNumber        電話番号
     * @param prefectureId     都道府県ID
     * @param cityId           市区町村ID
     * @param blockId          町域番地ID
     * @param deliveryOther    住所
     * @param mailAddress      メール
     * @param comment          要望
     * @param updOperCd        更新者
     * @param updDateTime      更新日時
     */
    @Modifying
    @Query(value = "update o_delivery_order_summary    "
        + "set delivery_type_flag = :deliveryTypeFlag, "
        + "status = :status,                           "
        + "customer_name = :customerName,              "
        + "tel_number = :telNumber,                    "
        + "prefecture_id = :prefectureId,              "
        + "city_id = :cityId,                          "
        + "block_id = :blockId,                        "
        + "delivery_other = :deliveryOther,            "
        + "mail_address = :mailAddress,                "
        + "comment = :comment,                         "
        + "upd_oper_cd = :updOperCd,                   "
        + "upd_date_time = :updDateTime,               "
        + "version = version + 1                       "
        + "where store_id = :storeId                   "
        + "and order_summary_id = :orderSummaryId      "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateDeliveryOrderInDelivery(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("status") String status,
        @Param("customerName") String customerName,
        @Param("telNumber") String telNumber,
        @Param("prefectureId") String prefectureId,
        @Param("cityId") String cityId,
        @Param("blockId") String blockId,
        @Param("deliveryOther") String deliveryOther,
        @Param("mailAddress") String mailAddress,
        @Param("comment") String comment,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払い金額更新処理(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE ODeliveryOrderSummary "
        + "SET updOperCd = :updOperCd,"
        + "updDateTime = :updDateTime, "
        + "delFlag = 0, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND delFlag = 1 ")
    Integer updatePaymentAmountWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払い金額更新処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    注文金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE ODeliveryOrderSummary "
        + "SET paymentAmount = paymentAmount + :orderAmount,"
        + "updOperCd = :updOperCd,"
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND delFlag = 0")
    void updatePaymentAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払い金額更新処理(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    注文金額
     * @param orderId        注文ID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_delivery_order_summary "
        + "SET payment_amount = payment_amount + :orderAmount,"
        + "order_amount = order_amount + :orderAmount - ("
        + "SELECT foreign_tax FROM o_order WHERE store_id = :storeId AND order_id = :orderId), "
        + "version = version + 1,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND del_flag = 0", nativeQuery = true)
    void updatePaymentAmountWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文サマリ回復処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param delFlag        削除フラグ
     */
    void deleteByStoreIdAndOrderSummaryIdAndDelFlag(String storeId, String orderSummaryId,
        Integer delFlag);

    /**
     * 注文情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文情報
     */
    ODeliveryOrderSummary findByStoreIdAndOrderSummaryId(String storeId, String orderSummaryId);

    /**
     * 注文情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 注文サマリID
     * @param languages     言語
     * @return 注文情報
     */
    @Query(value = "SELECT t3.order_id AS orderId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.item_id AS itemId, "
        + "t2.foreign_tax AS foreignTax, "
        + "t5.item_name ->> :languages AS itemName, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_count AS itemCount, "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount, "
        + "t6.option_name ->> :languages AS optionName, "
        + "t7.classification AS classification "
        + "FROM o_delivery_order_summary t1 "
        + "LEFT JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t2.del_flag = 0 "
        + "LEFT JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.del_flag = 0 "
        + "LEFT JOIN o_order_details_option t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND t3.order_detail_id = t4.order_detail_id "
        + "AND t4.del_flag = 0 "
        + "LEFT JOIN m_item t5 "
        + "ON t3.store_id = t5.store_id "
        + "AND t3.item_id = t5.item_id "
        + "AND t5.del_flag = 0 "
        + "LEFT JOIN m_option t6 "
        + "ON t4.store_id = t6.store_id "
        + "AND t4.item_option_type_code = t6.option_type_cd "
        + "AND t4.item_option_code = t6.option_code "
        + "AND t6.del_flag = 0 "
        + "LEFT JOIN m_option_type t7 "
        + "ON t6.store_id = t7.store_id "
        + "AND t6.option_type_cd = t7.option_type_cd "
        + "AND t7.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t3.item_id ASC, t4.item_option_type_code ASC, "
        + "t4.item_option_code ASC", nativeQuery = true)
    List<Map<String, Object>> findOrderInfoByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages);

    /**
     * キッチンと客様用伝票データ取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 注文サマリID
     * @return キッチンと客様用伝票データ
     */
    ODeliveryOrderSummary findByStoreIdAndReceivablesId(String storeId, String receivablesId);


    /**
     * キッチンと客様用伝票データ取得処理(出前).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderIds       注文IDリスト
     * @param orderDetailId  注文明細ID
     * @return キッチンと客様用伝票データ
     */
    @Query(value = "SELECT new com.cnc.qr.common.shared.model.SlipDeliveryDto(" +
        "t1.orderSummaryId, " +
        "t1.orderAmount," +
        "t1.paymentAmount," +
        "t2.orderId," +
        "t2.comment," +
        "t2.foreignTax," +
        "t3.orderDetailId," +
        "t3.itemId," +
        "t3.itemPrice," +
        "t3.itemCount," +
        "t3.itemClassification," +
        "t3.returnOrderDetailId," +
        "t3.itemReturnId," +
        "t5.optionTypeName," +
        "t4.itemOptionTypeCode," +
        "t4.itemOptionCode," +
        "t4.itemOptionCount," +
        "t6.optionName," +
        "t7.itemName," +
        "t7.itemType," +
        "t7.optionFlag," +
        "t8.kitchenName," +
        "t10.printIp," +
        "t10.bluetoothName," +
        "t10.brandCode," +
        "t10.connectionMethodCode," +
        "t10.printSize," +
        "t5.classification, " +
        "t15.taxCode, " +
        "t15.taxReliefApplyType, " +
        "t12.receptionNo ) " +
        "from ODeliveryOrderSummary t1 " +
        "INNER JOIN OOrder t2 " +
        "ON t2.orderSummaryId = t1.orderSummaryId " +
        "AND t2.storeId = t1.storeId " +
        "AND (t2.orderId in( :orderIds) OR :orderIds is null)" +
        "AND t2.delFlag = 0 " +
        "INNER JOIN OOrderDetails t3 " +
        "ON t3.storeId = t2.storeId " +
        "AND t3.orderId = t2.orderId " +
        "AND t3.delFlag = 0 " +
        "AND (t3.orderDetailId =  :orderDetailId or :orderDetailId is null) " +
        "INNER JOIN MItem t7 " +
        "ON t7.itemId = t3.itemId " +
        "AND t7.storeId = t1.storeId " +
        "AND t7.delFlag = 0 " +
        "INNER JOIN MStore t13 " +
        "ON t13.storeId = t7.storeId " +
        "AND t13.delFlag = 0 " +
        "INNER JOIN MBusiness t14 " +
        "ON t14.businessId = t13.businessId " +
        "AND t14.delFlag = 0 " +
        "INNER JOIN MTax t15 " +
        "ON t15.businessId = t14.businessId " +
        "AND t15.taxId = t7.taxId " +
        "AND t15.delFlag = 0 " +
        "INNER JOIN MKitchen t8 " +
        "ON t8.kitchenId = t7.kitchenId " +
        "AND t8.storeId = t7.storeId " +
        "AND t8.delFlag = 0 " +
        "INNER JOIN RKitchenPrint t9 " +
        "ON t9.kitchenId = t8.kitchenId " +
        "AND t9.storeId = t8.storeId " +
        "AND t9.delFlag = 0 " +
        "INNER JOIN MPrint t10 " +
        "ON t10.printId = t9.printId " +
        "AND t10.storeId = t9.storeId " +
        "AND t10.delFlag = 0 " +
        "INNER JOIN OReceivables t12 " +
        "ON t12.receivablesId = t1.receivablesId " +
        "AND t12.storeId = t1.storeId " +
        "AND t12.delFlag = 0 " +
        "LEFT JOIN OOrderDetailsOption t4 " +
        "ON t4.orderDetailId = t3.orderDetailId " +
        "AND t4.storeId = t3.storeId " +
        "AND t4.delFlag = 0 " +
        "LEFT JOIN MOptionType t5 " +
        "ON t5.optionTypeCd = t4.itemOptionTypeCode " +
        "AND t5.storeId = t4.storeId " +
        "AND t5.delFlag = 0 " +
        "LEFT JOIN MOption t6 " +
        "ON t6.optionTypeCd = t4.itemOptionTypeCode " +
        "AND t6.optionCode = t4.itemOptionCode " +
        "AND t6.storeId = t4.storeId " +
        "AND t6.delFlag = 0 " +
        "WHERE t1.orderSummaryId = :orderSummaryId " +
        "AND t1.storeId = :storeId " +
        "AND t1.delFlag = 0 "
    )
    List<SlipDeliveryDto> findByDeliveryOrderSummaryIdAndByStoreId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderIds") List<Integer> orderIds, @Param("orderDetailId") Integer orderDetailId);


    /**
     * 注文情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param languages      言語
     * @return 注文情報
     */
    @Query(value = "SELECT t1.customer_name AS customerName, "
        + "t1.comment AS comment, "
        + "t1.catering_charge_amount AS cateringChargeAmount, "
        + "t1.tel_number AS telNumber, "
        + "t1.delivery_other AS deliveryOther, "
        + "t2.prefecture_name ->> :languages AS prefectureName, "
        + "t2.city_name ->> :languages AS cityName, "
        + "t2.block_name ->> :languages AS blockName "
        + "FROM o_delivery_order_summary t1 "
        + "INNER JOIN m_area t2 "
        + "ON t2.prefecture_id = t1.prefecture_id "
        + "AND t2.city_id = t1.city_id "
        + "AND t2.block_id = t1.block_id "
        + "AND t2.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.order_summary_id = :orderSummaryId "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findCustomInfo(
        @Param("storeId") String storeId, @Param("orderSummaryId") String orderSummaryId,
        @Param("languages") String languages);

    /**
     * オーダー商品取得取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @return 注文情報
     */
    @Query(value = "SELECT " +
        "t3.item_id AS itemId, " +
        "t1.takeout_flag AS takeoutFlag, " +
        "t3.item_price * t3.item_count AS itemPrice " +
        "from o_delivery_order_summary t1 " +
        "INNER JOIN  o_order t2 " +
        "ON t2.order_summary_id = t1.order_summary_id " +
        "AND t2.store_id = t1.store_id " +
        "AND t2.order_id = :orderId " +
        "AND t2.del_flag = 0 " +
        "INNER JOIN o_order_details t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.order_id = t2.order_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.orderSummaryId = :orderSummaryId " +
        "AND t1.storeId = :storeId " +
        "AND t1.delFlag = 0 ", nativeQuery = true)
    List<Map<String, Object>> getDeliveryOrderItem(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId);

    /**
     * 出前注文状態変更.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param status         状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "UPDATE ODeliveryOrderSummary t1 "
        + "SET t1.status = :status, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.delFlag = 0 "
        + "AND t1.orderSummaryId = :orderSummaryId "
        + "AND t1.storeId = :storeId ")
    void updateDeliveryStatus(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("status") String status,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
