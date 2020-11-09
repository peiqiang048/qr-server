package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PPayment;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 支払テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface PPaymentRepository extends JpaRepository<PPayment, Long> {

    /**
     * MaxPaymentId取得.
     *
     * @param storeId 店舗ID
     * @return 支払ID
     */
    @Query(value = "SELECT t1.payment_id " +
        "FROM p_payment t1 " +
        "WHERE t1.store_id = :storeId " +
        "ORDER BY t1.payment_id desc " +
        "LIMIT 1", nativeQuery = true)
    Integer getMaxPaymentId(@Param("storeId") String storeId);

    /**
     * 支払テーブル登録.
     */
    @Query(value = "" +
        "INSERT INTO p_payment(" +
        "store_id," +
        "payment_id," +
        "order_summary_id," +
        "payment_amount," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":paymentId," +
        ":orderSummaryId," +
        ":paymentAmount," +
        ":delFlag," +
        ":insOperCd," +
        ":insDateTime," +
        ":updOperCd," +
        ":updDateTime," +
        ":version)" +
        "ON conflict(store_id,order_summary_id) " +
        "DO UPDATE SET payment_amount = p_payment.payment_amount + :paymentAmount," +
        "upd_oper_cd = :updOperCd," +
        "upd_date_time = :updDateTime," +
        "version = p_payment.version + 1 returning p_payment.payment_id ", nativeQuery = true)
    Integer insertOrUpdate(@Param("storeId") String storeId,
        @Param("paymentId") Integer paymentId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("paymentAmount") BigDecimal paymentAmount,
        @Param("delFlag") Integer delFlag,
        @Param("insOperCd") String insOperCd,
        @Param("insDateTime") ZonedDateTime insDateTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("version") Integer version);

    /**
     * 支払情報を削除する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param code           削除フラグ
     */
    void deleteByStoreIdAndOrderSummaryIdAndDelFlag(String storeId, String orderSummaryId,
        Integer code);

    /**
     * 支払テーブル.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param refundsAmount  返金金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Query(value = "" +
        "UPDATE p_payment " +
        "SET payment_amount = payment_amount - :refundsAmount," +
        "upd_oper_cd = :updOperCd," +
        "upd_date_time = :updDateTime " +
        "WHERE store_id = :storeId " +
        "AND order_summary_id = :orderSummaryId " +
        "AND del_flag = 0 returning payment_id", nativeQuery = true)
    Integer updatePaymentAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("refundsAmount") BigDecimal refundsAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
