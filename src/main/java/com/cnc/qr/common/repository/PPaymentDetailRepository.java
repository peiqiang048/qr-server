package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PPaymentDetail;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 支払明細テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface PPaymentDetailRepository extends JpaRepository<PPaymentDetail, Long> {

    /**
     * MaxPaymentDetailId取得.
     *
     * @param storeId 店舗ID
     * @return 支払明細ID
     */
    @Query(value = "SELECT t1.payment_detail_id " +
        "FROM p_payment_detail t1 " +
        "WHERE t1.store_id = :storeId " +
        "ORDER BY t1.payment_detail_id desc " +
        "LIMIT 1", nativeQuery = true)
    Integer getMaxPaymentDetailId(@Param("storeId") String storeId);

    /**
     * 支払明細テーブル登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO p_payment_detail(" +
        "store_id," +
        "payment_detail_id," +
        "payment_id," +
        "order_summary_id," +
        "order_id," +
        "payment_method_code," +
        "payment_amount," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":paymentDetailId," +
        ":paymentId," +
        ":orderSummaryId," +
        ":orderId," +
        ":paymentMethodCode," +
        ":paymentAmount," +
        ":delFlag," +
        ":insOperCd," +
        ":insDateTime," +
        ":updOperCd," +
        ":updDateTime," +
        ":version)", nativeQuery = true)
    void insert(@Param("storeId") String storeId,
        @Param("paymentDetailId") Integer paymentDetailId,
        @Param("paymentId") Integer paymentId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("paymentMethodCode") String paymentMethodCode,
        @Param("paymentAmount") BigDecimal paymentAmount,
        @Param("delFlag") Integer delFlag,
        @Param("insOperCd") String insOperCd,
        @Param("insDateTime") ZonedDateTime insDateTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("version") Integer version);


    /**
     * 会計方式と金額取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderIds       注文IDリスト
     * @return 会計方式と金額情報
     */
    @Query(value = "select  " +
        "paymentMethodCode," +
        "paymentAmount " +
        "from PPaymentDetail " +
        "where orderSummaryId = :orderSummaryId " +
        "AND orderId in (:orderIds) " +
        "and delFlag = 0 " +
        "and storeId = :storeId ")
    List<PPaymentDetail> findbyOrderSummaryIdAndOrderId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderIds") List<Integer> orderIds);

    /**
     * 支払明細情報を削除する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param code           削除フラグ
     */
    void deleteByStoreIdAndOrderSummaryIdAndDelFlag(String storeId, String orderSummaryId,
        Integer code);

    /**
     * 現金支払金額取得.
     *
     * @param storeId           店舗ID
     * @param orderSummaryId    注文サマリID
     * @param paymentMethodCode 支払方式コード
     * @return 現金支払金額
     */
    @Query(value = "SELECT SUM(payment_amount) " +
        "FROM p_payment_detail " +
        "WHERE store_id = :storeId " +
        "AND order_summary_id = :orderSummaryId " +
        "AND payment_method_code = :paymentMethodCode " +
        "AND del_flag = 0 ", nativeQuery = true)
    BigDecimal getCashPayAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("paymentMethodCode") String paymentMethodCode);


    /**
     * 現金支払金額取得.
     *
     * @param storeId           店舗ID
     * @param receivablesId     受付ID
     * @param paymentMethodCode 支払方式コード
     * @return 現金支払金額
     */
    @Query(value = "SELECT SUM(t1.paymentAmount) "
        + "FROM PPaymentDetail t1 "
        + "INNER JOIN OOrderSummary t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.orderSummaryId = t2.orderSummaryId "
        + "WHERE t1.storeId = :storeId "
        + "AND t2.receivablesId = :receivablesId "
        + "AND t1.paymentMethodCode = :paymentMethodCode "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 ")
    BigDecimal getCashPayAmountByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("paymentMethodCode") String paymentMethodCode);

    /**
     * 支払明細情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 支払明細情報
     */
    @Query(value =
        "SELECT distinct MAX(payment_method_code) as paymentMethodCode,"
            + "MAX(payment_amount) AS  paymentAmount "
            +
            "FROM p_payment_detail " +
            "WHERE store_id = :storeId " +
            "AND order_summary_id = :orderSummaryId " +
            "AND del_flag = 0 "
            + "GROUP BY order_summary_id,order_id,payment_method_code "
            + "HAVING sum(payment_amount)>0", nativeQuery = true)
    List<Map<String, Object>> findByPayment(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);


    /**
     * 現金点検精算金額取得.
     *
     * @param storeId           店舗ID
     * @param startTime         開始日時
     * @param endTime           終了日時
     * @param paymentMethodCode 支払方式コード
     * @return 現金点検精算金額
     */
    @Query(value = "SELECT SUM(payment_amount) " +
        "FROM p_payment_detail " +
        "WHERE store_id = :storeId " +
        "AND payment_method_code = :paymentMethodCode " +
        "AND ins_date_time >= :startTime " +
        "AND ins_date_time <= :endTime " +
        "AND del_flag = 0 ", nativeQuery = true)
    BigDecimal getInspectionSettleCashPayAmount(@Param("storeId") String storeId,
        @Param("startTime") ZonedDateTime startTime,
        @Param("endTime") ZonedDateTime endTime,
        @Param("paymentMethodCode") String paymentMethodCode);
}
