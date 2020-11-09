package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OOrderDetailsOption;
import com.cnc.qr.common.entity.OOrderSummary;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 注文明細オプションテーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OOrderDetailsOptionRepository extends JpaRepository<OOrderDetailsOption, Long> {

    /**
     * 注文商品オプション回復.
     *
     * @param storeId 店舗ID
     * @param idList  注文IDリスト
     */
    @Modifying
    @Query(value = ""
        + "delete from o_order_details_option "
        + "WHERE store_id = :storeId "
        + "AND order_detail_id in :idList ", nativeQuery = true)
    void deleteOrderItemOptions(@Param("storeId") String storeId,
        @Param("idList") List<Integer> idList);

    /**
     * 注文明細オプション状態更新処理(先払専用).
     *
     * @param storeId     店舗ID
     * @param orderId     注文ID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderDetailsOption t1 "
        + "SET t1.delFlag = 0, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND EXISTS ("
        + "  SELECT true "
        + "  FROM OOrderDetails t2"
        + "  WHERE t2.storeId = :storeId"
        + "  AND t2.orderId = :orderId"
        + "  AND t2.orderDetailId = t1.orderDetailId"
        + "  AND t2.storeId = t1.storeId)")
    void updateDelFlagByOrderId(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文明細オプション削除処理.
     *
     * @param storeId       店舗ID
     * @param orderDetailId 注文明細ID
     */
    void deleteByStoreIdAndOrderDetailId(String storeId, Integer orderDetailId);

    /**
     * 注文サマリ情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文サマリ情報
     */
    @Query(value = "SELECT  t4 " +
        "FROM OOrderSummary t1 " +
        "INNER JOIN OOrder t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.orderSummaryId = t2.orderSummaryId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN OOrderDetails t3 " +
        "ON t2.storeId = t3.storeId " +
        "AND t2.orderId = t3.orderId " +
        "AND t3.delFlag = 0 " +
        "INNER JOIN OOrderDetailsOption t4 " +
        "ON t3.storeId = t4.storeId " +
        "AND t3.orderDetailId = t4.orderDetailId " +
        "AND t4.delFlag = 0 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.orderSummaryId = :orderSummaryId " +
        "AND t1.delFlag = 0 ")
    List<OOrderDetailsOption> getOrderOptionList(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文明細情報削除(割勘専用).
     *
     * @param storeId           店舗ID
     * @param orderDetailIdList 注文明細IDリスト
     * @param updOperCd         更新者
     * @param updDateTime       更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderDetailsOption t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.orderDetailId in (:orderDetailIdList) ")
    void deleteItemDetails(@Param("storeId") String storeId,
        @Param("orderDetailIdList") List<Integer> orderDetailIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
