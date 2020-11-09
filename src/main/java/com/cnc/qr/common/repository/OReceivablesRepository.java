package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.core.order.model.OreceivablesGridDto;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 受付テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OReceivablesRepository extends JpaRepository<OReceivables, Long> {


    /**
     * 受付テーブル情報取得.
     *
     * @param storeId       店舗ID
     * @param delFlag       削除フラグ
     * @param receivablesId 受付ID
     * @return 受付情報
     */
    OReceivables findByStoreIdAndDelFlagAndReceivablesId(String storeId, Integer delFlag,
        String receivablesId);

    /**
     * 受付番号取得.
     *
     * @param storeId   店舗ID
     * @param startTime 開始日時
     * @param endTime   終了日時
     * @return 受付番号
     */
    @Query(value = "SELECT COALESCE(MAX(t1.reception_no), 0) + 1 AS receptionNo "
        + "FROM o_receivables t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reception_time >= :startTime "
        + "AND t1.reception_time < :endTime "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Integer getReceptionNoByReceptionTime(@Param("storeId") String storeId,
        @Param("startTime") ZonedDateTime startTime, @Param("endTime") ZonedDateTime endTime);

    /**
     * 受付廃棄.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    @Modifying
    @Query(value = "update OReceivables "
        + "set delFlag = 1, "
        + "version = version + 1 "
        + "where delFlag = 0 "
        + "and receivablesId = :receivablesId "
        + "and storeId = :storeId ")
    void updateByDelFlag(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 受付状態変更.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    @Modifying
    @Query(value = "update OReceivables "
        + "set delFlag = 0, "
        + "version = version + 1 "
        + "where delFlag = 1 "
        + "and receivablesId = :receivablesId "
        + "and storeId = :storeId ")
    void updateDelFlagByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 配席一覧取得.
     *
     * @param storeId     店舗ID
     * @param start       開始日時
     * @param takeoutFlag テイクアウト区分
     * @return 配席一覧
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.OreceivablesGridDto("
        + "t1.receivablesId, "
        + "t1.receptionNo, "
        + "t1.customerCount, "
        + "t1.receptionTime) "
        + "FROM  OReceivables t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.delFlag = 0 "
        + "AND t1.receptionTime > :start "
        + "AND t1.receivablesId NOT IN( "
        + "    SELECT "
        + "        t2.receivablesId "
        + "    FROM OOrderSummary t2 "
        + "    WHERE t2.storeId = :storeId "
        + "    AND t2.delFlag = 0 "
        + "    AND (t2.takeoutFlag = :takeoutFlag "
        + "    OR t2.tableId is not null))"
        + "ORDER BY  t1.receptionTime ASC ")
    List<OreceivablesGridDto> findByReception(
        @Param("storeId") String storeId, @Param("start") ZonedDateTime start,
        @Param("takeoutFlag") String takeoutFlag);

    /**
     * 注文詳細情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param languages     言語
     * @return 配席一覧
     */
    @Query(value = "SELECT TO_CHAR(t8.reception_time, 'YYYY-MM-DD HH24:MI:SS') AS receptionTime, "
        + "t8.reception_no AS receptionNo, "
        + "t1.order_amount AS orderAmount, "
        + "t1.payment_amount AS paymentAmount, "
        + "t1.price_discount_amount AS priceDiscountAmount, "
        + "t1.price_discount_rate AS priceDiscountRate, "
        + "t9.table_name AS tableName, "
        + "t1.takeout_flag AS takeoutFlag, "
        + "t2.foreign_tax AS foreignTax, "
        + "t3.order_id AS orderId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.item_id AS itemId, "
        + "t5.item_name ->> :languages AS itemName, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_count AS itemCount, "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount, "
        + "t6.option_name ->> :languages AS optionName, "
        + "t7.classification AS classification, "
        + "t3.item_classification AS itemClassification, "
        + "t3.item_status AS itemSureStatus, "
        + "t1.payment_type AS paymentType "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
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
        + "LEFT JOIN o_receivables t8 "
        + "ON t1.store_id = t8.store_id "
        + "AND t1.receivables_id = t8.receivables_id "
        + "AND t8.del_flag = 0 "
        + "LEFT JOIN m_table t9 "
        + "ON t1.store_id = t9.store_id "
        + "AND t1.table_id = t9.table_id "
        + "AND t9.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "ORDER BY t3.item_id ASC, t4.item_option_type_code ASC, "
        + "t4.item_option_code ASC", nativeQuery = true)
    List<Map<String, Object>> findOrderInfo(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages);

    /**
     * 受付更新.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param customerCount 人数
     */
    @Modifying
    @Query(value = "update o_receivables "
        + "set customer_count = :customerCount, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and receivables_id = :receivablesId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateByStoreIdAndReceivablesIdAndDelFlag(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("customerCount") Integer customerCount);

    /**
     * 注文詳細情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 注文詳細情報
     */
    @Query(value = "SELECT t1.customer_count AS customerCount, "
        + "t3.table_name AS tableName, "
        + "t2.reception_no AS receptionNo, "
        + "t1.order_amount AS orderAmount, "
        + "t1.takeout_flag AS takeoutFlag,"
        + "t1.table_id AS tableId "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_receivables t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.receivables_id = t2.receivables_id "
        + "LEFT JOIN m_table t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.table_id = t3.table_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findOrderInfoByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId);

    /**
     * 席解除一覧情報取得.
     *
     * @param storeId     店舗ID
     * @param seatRelease 席解除フラグ
     * @param payStatus   支払状態
     * @return 席解除一覧
     */
    @Query(value = "SELECT TO_CHAR(t2.reception_time, 'YYYY-MM-DD HH24:MI:SS') AS receptionTime, "
        + "t2.reception_no AS receptionNo, "
        + "t1.payment_amount AS orderAmount, "
        + "t3.table_name AS tableName, "
        + "t1.table_id AS tableId, "
        + "t1.receivables_id AS receivablesId "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_receivables t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.receivables_id = t2.receivables_id "
        + "LEFT JOIN m_table t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.table_id = t3.table_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.seat_release = :seatRelease "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t1.payment_amount > 0 "
        + "AND NOT EXISTS ( "
        + "  SELECT true "
        + "  FROM o_order t4"
        + "  WHERE t1.store_id = t4.store_id"
        + "  AND t1.order_summary_id = t4.order_summary_id"
        + "  AND t4.pay_status = :payStatus"
        + "  AND t4.del_flag = 0 ) "
        + "ORDER BY t2.reception_time ASC", nativeQuery = true)
    List<Map<String, Object>> findSeatReleaseInfoByStoreId(@Param("storeId") String storeId,
        @Param("seatRelease") String seatRelease, @Param("payStatus") String payStatus);

    /**
     * 指定受付ID削除.
     *
     * @param storeId           店舗ID
     * @param receivablesIdList 受付IDリスト
     */
    @Modifying
    @Query(value = "delete from o_receivables "
        + "where store_id = :storeId and receivables_id in :receivablesIdList", nativeQuery = true)
    void deleteReceivables(@Param("storeId") String storeId,
        @Param("receivablesIdList") List<String> receivablesIdList);

    /**
     * 获取已确认的放题コース数.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param itemStatus    状態
     * @param course        コース商品区分
     * @param buffet        放题商品区分
     * @return 已确认的放题コース数
     */
    @Query(value = "SELECT count(*) "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "INNER JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.del_flag = 0 "
        + "INNER JOIN m_item t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND t3.item_id = t4.item_id "
        + "AND t4.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t3.item_status = :itemStatus "
        + "AND (t4.item_type = :course OR t4.item_type = :buffet) ", nativeQuery = true)
    Integer getOrderBuffetCourseCount(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("itemStatus") String itemStatus,
        @Param("course") String course, @Param("buffet") String buffet);

    /**
     * 获取放题コース以外商品数.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param itemStatus    状態
     * @return 放题コース以外商品数
     */
    @Query(value = "SELECT count(*) "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "INNER JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.del_flag = 0 "
        + "INNER JOIN m_item t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND t3.item_id = t4.item_id "
        + "AND t4.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t4.item_type NOT IN :itemStatus ", nativeQuery = true)
    Integer getOrderNotBuffetCourseCount(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("itemStatus") List<String> itemStatus);

    /**
     * 受付回復.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    void deleteByStoreIdAndReceivablesId(String storeId, String receivablesId);
}
