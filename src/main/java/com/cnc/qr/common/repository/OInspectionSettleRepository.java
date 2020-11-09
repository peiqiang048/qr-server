package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OInspectionSettle;
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
 * 点検精算テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OInspectionSettleRepository extends JpaRepository<OInspectionSettle, Long> {

    /**
     * 繰越金取得.
     *
     * @param storeId    店舗ID
     * @param settleType 精算区分
     * @return 繰越金
     */
    @Query(value =
        "SELECT t1.next_day_transferred_amount AS nextDayTransferredAmount,"
            + "TO_CHAR(t1.inspection_settle_date, 'YYYY-MM-DD HH24:MI:SS') AS inspectionSettleDate "
            + "FROM o_inspection_settle t1 "
            + "WHERE t1.store_id = :storeId "
            + "AND t1.settle_type = :settleType "
            + "AND t1.del_flag = 0 "
            + "order by t1.inspection_settle_date desc "
            + "limit 1 ", nativeQuery = true)
    Map<String, Object> getNextDayTransferredAmountAndInspectionSettleDate(
        @Param("storeId") String storeId, @Param("settleType") String settleType);

    /**
     * 出金取得.
     *
     * @param storeId    店舗ID
     * @param settleType 精算区分
     * @param startTime  開始日時
     * @param endTime    終了日時
     * @return 出金
     */
    @Query(value =
        "SELECT t1.out_amount AS  outAmount "
            + "FROM o_inspection_settle t1 "
            + "WHERE t1.store_id = :storeId "
            + "AND t1.settle_type = :settleType "
            + "AND t1.del_flag = 0 "
            + "AND t1.out_amount > 0 "
            + "AND t1.ins_date_time >= :startTime "
            + "AND t1.ins_date_time <= :endTime "
            + "order by t1.inspection_settle_date desc ", nativeQuery = true)
    List<BigDecimal> getWithdrawalAmount(
        @Param("storeId") String storeId, @Param("settleType") String settleType,
        @Param("startTime") ZonedDateTime startTime, @Param("endTime") ZonedDateTime endTime);

    /**
     * 点検精算印刷のNo取得.
     *
     * @param storeId    店舗ID
     * @param settleType 精算区分
     * @return 印刷No
     */
    @Query(value = "SELECT TO_CHAR( count(t1.*), 'FM00000') AS no "
        + "FROM o_inspection_settle t1 "
        + "WHERE t1.store_id = :storeId AND t1.settle_type = :settleType AND t1.del_flag = 0", nativeQuery = true)
    String getInspectionSettlePrintNo(
        @Param("storeId") String storeId, @Param("settleType") String settleType);

    /**
     * 精算回数取得.
     *
     * @param storeId    店舗ID
     * @param settleType 精算区分
     * @param startTime  開始日時
     * @param endTime    終了日時
     * @return 精算回数
     */
    @Query(value =
        "SELECT TO_CHAR(t1.inspection_settle_date, 'YYYY-MM-DD HH24:MI:SS') AS inspectionSettleDate "
            + "FROM o_inspection_settle t1 "
            + "WHERE t1.store_id = :storeId "
            + "AND t1.settle_type = :settleType "
            + "AND t1.inspection_settle_date >= :startTime "
            + "AND t1.inspection_settle_date <= :endTime "
            + "AND t1.del_flag = 0 ", nativeQuery = true)
    List<String> getSettleDate(
        @Param("storeId") String storeId, @Param("settleType") String settleType,
        @Param("startTime") ZonedDateTime startTime, @Param("endTime") ZonedDateTime endTime);

    /**
     * 精算削除.
     *
     * @param storeId              店舗ID
     * @param settleType           精算区分
     * @param inspectionSettleDate 点検精算日
     */
    @Modifying
    @Query(value =
        "update o_inspection_settle "
            + "set del_flag = 1 , "
            + "version = version + 1 "
            + "where TO_CHAR(inspection_settle_date, 'YYYY-MM-DD HH24:MI:SS') = :inspectionSettleDate "
            + "and  store_id = :storeId "
            + "and  settle_type = :settleType "
            + "AND del_flag = 0 ", nativeQuery = true)
    void updateSettleDelFlag(
        @Param("storeId") String storeId, @Param("settleType") String settleType,
        @Param("inspectionSettleDate") String inspectionSettleDate);
}
