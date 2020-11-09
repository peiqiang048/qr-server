package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OOrder;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 注文テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OOrderRepository extends JpaRepository<OOrder, Long> {

    /**
     * 注文データ取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 注文データ
     */
    List<OOrder> findByStoreIdAndDelFlagOrderByOrderIdDesc(String storeId, Integer delFlag);

    /**
     * 支払状態更新.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param payStatus      支払状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrder "
        + "SET payStatus = :payStatus,"
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1  "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND orderId = :orderId "
        + "AND delFlag = 0")
    void updateStatus(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("payStatus") String payStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文回復処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     */
    void deleteByStoreIdAndOrderSummaryIdAndOrderId(String storeId, String orderSummaryId,
        Integer orderId);

    /**
     * 支払明細情報を削除する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param payStatus      支払状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order "
        + "SET pay_status = :payStatus,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId ", nativeQuery = true)
    void updatePaymentStatus(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("payStatus") String payStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文情報取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @param orderId 注文ID
     * @return 注文情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OOrder findByStoreIdAndDelFlagAndOrderId(String storeId, Integer delFlag,
        Integer orderId);

    /**
     * 前払い外税取得.
     *
     * @param storeId  店舗ID
     * @param orderIds 注文IDリスト
     * @return 前払い外税
     */
    @Query(value = "SELECT sum(o.foreign_tax) AS foreignTax " +
        "FROM o_order o " +
        "WHERE o.store_id = :storeId " +
        "AND o.order_id IN ( :orderIds) " +
        "AND o.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findByStoreIdSotoTax(@Param("storeId") String storeId,
        @Param("orderIds") List<Integer> orderIds);

    /**
     * 支払状態変更.
     *
     * @param storeId     店舗ID
     * @param orderId     注文ID
     * @param payStatus   支払状態
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order "
        + "SET pay_status = :payStatus,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE del_Flag = 0 "
        + "AND store_id = :storeId "
        + "AND order_id = :orderId", nativeQuery = true)
    void updatePayStatus(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId,
        @Param("payStatus") String payStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 获取先支付情况下的未支付订单数.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param payStatus      支払状態
     * @return 未支付订单数
     */
    @Query(value = "SELECT count(*) " +
        "FROM o_order o " +
        "WHERE o.store_id = :storeId " +
        "AND o.order_summary_id = :orderSummaryId " +
        "AND o.pay_status = :payStatus " +
        "AND o.del_flag = 0 ", nativeQuery = true)
    Integer getUnPayOrderCount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("payStatus") String payStatus);

    /**
     * 获取先支付情况下的未支付订单数.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param payStatus      支払状態
     * @return 未支付订单数
     */
    @Query(value = "SELECT order_id " +
        "FROM o_order o " +
        "WHERE o.store_id = :storeId " +
        "AND o.order_summary_id = :orderSummaryId " +
        "AND o.pay_status = :payStatus " +
        "AND o.del_flag = 0 ", nativeQuery = true)
    Integer getUnPayOrderId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("payStatus") String payStatus);

    /**
     * 税金を取得する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 税金
     */
    @Query(value = "SELECT SUM(foreign_tax) " +
        "FROM o_order " +
        "WHERE store_id = :storeId " +
        "AND order_summary_id = :orderSummaryId " +
        "AND del_flag = 0 ", nativeQuery = true)
    BigDecimal getForeignTax(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文状態更新(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param payStatus      支払状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrder "
        + "SET payStatus = :payStatus,"
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime,"
        + "delFlag = 0, "
        + "version = version + 1  "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND orderId = :orderId ")
    void updateStatusWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("payStatus") String payStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文IDを削除する.
     *
     * @param storeId 店舗ID
     * @param idList  注文IDリスト
     */
    @Modifying
    @Query(value = ""
        + "delete from o_order "
        + "WHERE store_id = :storeId "
        + "AND order_id in :idList ", nativeQuery = true)
    void deleteOrders(@Param("storeId") String storeId,
        @Param("idList") List<Integer> idList);

    /**
     * 注文サマリIDを取得する.
     *
     * @param storeId 店舗ID
     * @param orderId 注文ID
     * @return 注文サマリID
     */
    @Query(value = "SELECT order_summary_id " +
        "FROM o_order o " +
        "WHERE o.store_id = :storeId " +
        "AND o.order_id = :orderId " +
        "AND o.del_flag = 0 ", nativeQuery = true)
    String getOrderSummaryId(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId);


    /**
     * 外税金額変更.
     *
     * @param storeId     店舗ID
     * @param orderId     注文ID
     * @param foreignTax  外税金額
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order "
        + "SET foreign_tax = :foreignTax,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE del_Flag = 0 "
        + "AND store_id = :storeId "
        + "AND order_id = :orderId", nativeQuery = true)
    void updateForeignTax(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId,
        @Param("foreignTax") BigDecimal foreignTax,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文削除処理.
     *
     * @param storeId 店舗ID
     * @param orderId 注文ID
     */
    @Modifying
    @Query(value = ""
        + "delete from o_order t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.order_id = :orderId "
        + "AND NOT EXISTS ("
        + "  SELECT true "
        + "  FROM o_order_details t2 "
        + "  WHERE t1.store_id = t2.store_id "
        + "  AND t1.order_id = t2.order_id "
        + "  AND t2.del_Flag = 0)", nativeQuery = true)
    Integer deleteOrderWithDetails(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId);

    /**
     * 注文情報削除(割勘専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order "
        + "SET del_Flag = 1, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE del_Flag = 0 "
        + "AND store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND order_id NOT IN ("
        + "  SELECT t2.order_id "
        + "  FROM o_order_details t2 "
        + "  WHERE t2.store_id = :storeId "
        + "  AND t2.del_Flag = 0 "
        + "  GROUP BY t2.order_id)", nativeQuery = true)
    void deleteOrder(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
