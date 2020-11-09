package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OTaxAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 税額テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OTaxAmountRepository extends JpaRepository<OTaxAmount, Long> {


    /**
     * 税額テーブル更新処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "with  t1 as ( SELECT tax_amount_id "
        + "                       from o_tax_amount "
        + "                       where store_id = :storeId "
        + "                       AND order_summary_id = :orderSummaryId "
        + "                       AND (order_id = :orderId or :orderId = -1)"
        + "                       AND del_flag = 1  ORDER BY tax_amount_id DESC limit 1)  UPDATE  o_tax_amount t2 "
        + "SET del_flag = 0, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "from t1 "
        + "WHERE t1.tax_amount_id = t2.tax_amount_id ", nativeQuery = true)
    void updateDelFlagByOrderId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * 税額テーブル更新処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "UPDATE OTaxAmount t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.orderSummaryId = :orderSummaryId "
        + "AND (t1.orderId = :orderId or :orderId = -1) "
        + "AND t1.delFlag = 0 ")
    void deleteDelFlagByOrderId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * 点検精算時に税情報取得.
     *
     * @param storeId   店舗ID
     * @param startTime 開始日時
     * @param endTime   終了日時
     * @return 点検精算時に税情報
     */
    @Query(value = "SELECT sum(t1.consumption_amount) AS consumptionAmount, "
        + "sum(t1.foreign_tax) AS foreignTax,  "
        + "sum(t1.foreign_normal_amount) AS foreignNormalAmount,  "
        + "sum(t1.foreign_normal_object_amount) AS foreignNormalObjectAmount,  "
        + "sum(t1.foreign_relief_amount) AS foreignReliefAmount,  "
        + "sum(t1.foreign_relief_object_amount) AS foreignReliefObjectAmount,  "
        + "sum(t1.included_tax) AS includedTax,  "
        + "sum(t1.included_normal_amount) AS includedNormalAmount,  "
        + "sum(t1.included_normal_object_amount) AS includedNormalObjectAmount,  "
        + "sum(t1.included_relief_amount) AS includedReliefAmount,  "
        + "sum(t1.included_relief_object_amount) AS includedReliefObjectAmount  "

        + "FROM o_tax_amount t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.ins_oper_cd >= :startTime "
        + "AND t1.ins_oper_cd <= :endTime "
        + "AND t1.delFlag = 0 ", nativeQuery = true)
    Map<String, Object> findOTaxAmountsSum(@Param("storeId") String storeId,
        @Param("startTime") ZonedDateTime startTime,
        @Param("endTime") ZonedDateTime endTime);


    /**
     * 領収書の諸費税取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 印刷状態
     * @param delFlag        注文サマリID
     * @return 領収書の諸費税情報
     */
    List<OTaxAmount> findByStoreIdAndDelFlagAndOrderSummaryId(String storeId, Integer delFlag,
        String orderSummaryId);
}
