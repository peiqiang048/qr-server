package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OOrderDetails;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 注文明細テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OOrderDetailsRepository extends JpaRepository<OOrderDetails, Long> {

    /**
     * 注文明細テーブルデータ取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 注文明細テーブルデータ
     */
    List<OOrderDetails> findByStoreIdAndDelFlagOrderByOrderDetailIdDesc(String storeId,
        Integer delFlag);

    /**
     * 注文明細ID取得.
     *
     * @param storeId 店舗ID
     * @param orderId 注文ID
     * @return 注文明細ID
     */
    @Query(value = "SELECT t1.order_detail_id "
        + "FROM o_order_details t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.order_id = :orderId "
        + "AND t1.del_flag = 0", nativeQuery = true)
    List<Integer> getDetailIdList(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId);

    /**
     * 注文明細回復処理.
     *
     * @param storeId 店舗ID
     * @param orderId 注文ID
     */
    void deleteByStoreIdAndOrderId(String storeId, Integer orderId);

    /**
     * 注文状態更新処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param itemStatus    商品状態
     * @param orderIds      注文IDリスト
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderDetails t1 "
        + "SET t1.itemStatus = :itemStatus, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.delFlag = 0 "
        + "AND t1.storeId = :storeId "
        + "AND t1.orderId IN (:orderIds)"
        + "AND EXISTS ("
        + "  SELECT true "
        + "  FROM OOrderSummary t2"
        + "  INNER JOIN OOrder t3"
        + "  ON t2.storeId = t3.storeId"
        + "  AND t2.orderSummaryId = t3.orderSummaryId"
        + "  WHERE t2.receivablesId = :receivablesId"
        + "  AND t2.delFlag = 0"
        + "  AND t3.delFlag = 0"
        + "  AND t3.orderId = t1.orderId"
        + "  AND t2.storeId = t1.storeId)")
    void updateItemStatusByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("itemStatus") String itemStatus,
        @Param("orderIds") List<Integer> orderIds,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 返品原因情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 返品原因情報
     */
    @Query(value = "SELECT t1.item_return_id AS returnReasonCode, "
        + "t1.item_return_name ->> :languages AS returnReasonName "
        + "FROM o_item_return t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 ORDER BY t1.item_return_id ASC", nativeQuery = true)
    List<Map<String, Object>> getReturnReasonList(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 注文明細取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @param orderDetailId 注文明細ID
     * @return 注文明細情報
     */
    @Query(value = "SELECT t3.store_id AS storeId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.order_id AS orderId, "
        + "t3.item_id AS itemId, "
        + "t3.item_price AS itemPrice, "
        + "t1.order_summary_id AS orderSummaryId, "
        + "t3.item_count AS itemCount "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t2.store_id = t1.store_id "
        + "AND t2.order_summary_id = t1.order_summary_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t3.store_id = t2.store_id "
        + "AND t3.order_id = t2.order_id "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t2.order_id = :orderId "
        + "AND t3.order_detail_id = :orderDetailId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findOrderDetailIdByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("orderId") Integer orderId,
        @Param("orderDetailId") Integer orderDetailId);

    /**
     * 商品返品以外件数取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @return 商品返品以外件数
     */
    @Query(value = "SELECT SUM(t3.item_price * t3.item_count) " +
        "FROM o_order_summary t1 " +
        "INNER JOIN o_order t2 " +
        "ON t2.store_id = t1.store_id " +
        "AND t2.order_summary_id = t1.order_summary_id " +
        "INNER JOIN o_order_details t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.order_id = t2.order_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.receivables_id = :receivablesId " +
        "AND t2.order_id = :orderId " +
        "AND t3.item_classification <> '2' " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 " +
        "AND t3.del_flag = 0 ", nativeQuery = true)
    BigDecimal findItemCountByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("orderId") Integer orderId);

    /**
     * 商品返品件数取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @return 商品返品件数
     */
    @Query(value = "SELECT SUM(t3.item_price * t3.item_count) " +
        "FROM o_order_summary t1 " +
        "INNER JOIN o_order t2 " +
        "ON t2.store_id = t1.store_id " +
        "AND t2.order_summary_id = t1.order_summary_id " +
        "INNER JOIN o_order_details t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.order_id = t2.order_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.receivables_id = :receivablesId " +
        "AND t2.order_id = :orderId " +
        "AND t3.item_classification = '2' " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 " +
        "AND t3.del_flag = 0 ", nativeQuery = true)
    BigDecimal findItemReturnCountByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("orderId") Integer orderId);

    /**
     * 注文明細状態更新処理(先払専用).
     *
     * @param storeId     店舗ID
     * @param orderId     注文ID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderDetails t1 "
        + "SET t1.delFlag = 0, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.orderId = :orderId ")
    void updateDelFlagByOrderId(@Param("storeId") String storeId,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文明細削除処理.
     *
     * @param storeId 店舗ID
     * @param idList  注文IDリスト
     */
    @Modifying
    @Query(value = ""
        + "delete from o_order_details "
        + "WHERE store_id = :storeId "
        + "AND order_id in :idList ", nativeQuery = true)
    void deleteOrderItemDetails(@Param("storeId") String storeId,
        @Param("idList") List<Integer> idList);

    /**
     * 注文明細削除処理.
     *
     * @param storeId       店舗ID
     * @param orderId       注文ID
     * @param orderDetailId 注文明細ID
     */
    void deleteByStoreIdAndOrderIdAndOrderDetailId(String storeId, Integer orderId,
        Integer orderDetailId);

    /**
     * 放題注文时间取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param buffetId      放題商品ID
     * @return 注文时间
     */
    @Query(value = "select d.ins_date_time "
        + "from o_order_summary s, o_order o, o_order_details d "
        + "where s.store_id = o.store_id "
        + "and s.order_summary_id = o.order_summary_id "
        + "and o.store_id = d.store_id "
        + "and o.order_id = d.order_id "
        + "and s.del_flag = 0 "
        + "and o.del_flag = 0 "
        + "and d.del_flag = 0 "
        + "and s.store_id = :storeId "
        + "and s.receivables_id = :receivablesId "
        + "and d.item_id <> :buffetId "
        + "order by d.ins_date_time asc "
        + "limit 1 ", nativeQuery = true)
    Timestamp getOrderTime(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("buffetId") Integer buffetId);

    /**
     * 注文明細データ取得.
     *
     * @param storeId       店舗ID
     * @param orderDetailId 注文明細ID
     * @param delFlag       削除フラグ
     * @return 注文明細データ
     */
    OOrderDetails findByStoreIdAndOrderDetailIdAndDelFlag(String storeId, Integer orderDetailId,
        Integer delFlag);

    /**
     * 注文明細一覧取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 注文明細一覧
     */
    List<OOrderDetails> findByStoreIdAndDelFlagAndReturnOrderDetailIdIsNotNull(String storeId,
        Integer delFlag);

    /**
     * 注文明細取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @return 注文明細情報
     */
    @Query(value = "SELECT t3.store_id AS storeId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.order_id AS orderId, "
        + "t3.item_id AS itemId, "
        + "t3.item_price AS itemPrice, "
        + "t1.order_summary_id AS orderSummaryId, "
        + "t3.item_count AS itemCount "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t2.store_id = t1.store_id "
        + "AND t2.order_summary_id = t1.order_summary_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t3.store_id = t2.store_id "
        + "AND t3.order_id = t2.order_id "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t2.order_id = :orderId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findOrderDetailInfoByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("orderId") Integer orderId);

    /**
     * 注文サマリ情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文サマリ情報
     */
    @Query(value = "SELECT  t3 " +
        "FROM OOrderSummary t1 " +
        "INNER JOIN OOrder t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.orderSummaryId = t2.orderSummaryId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN OOrderDetails t3 " +
        "ON t2.storeId = t3.storeId " +
        "AND t2.orderId = t3.orderId " +
        "AND t3.delFlag = 0 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.orderSummaryId = :orderSummaryId " +
        "AND t1.delFlag = 0 ")
    List<OOrderDetails> getOrderDetailList(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文明細ID取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文明細ID
     */
    @Query(value = "SELECT  t3.orderDetailId " +
        "FROM OOrderSummary t1 " +
        "INNER JOIN OOrder t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.orderSummaryId = t2.orderSummaryId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN OOrderDetails t3 " +
        "ON t2.storeId = t3.storeId " +
        "AND t2.orderId = t3.orderId " +
        "AND t3.delFlag = 0 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.orderSummaryId = :orderSummaryId " +
        "AND t3.itemCount = 0 " +
        "AND t1.delFlag = 0 ")
    List<Integer> getOrdertailId(@Param("storeId") String storeId,
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
    @Query(value = "UPDATE OOrderDetails t1 "
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


    /**
     * 店員スマホ確認するとき商品変更するため更新処理
     *
     * @param storeId       店舗ID
     * @param orderDetailId 注文明細ID
     * @param itemCount     商品数量
     * @param itemPrice     商品金額
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderDetails t1 "
        + "SET t1.itemCount = :itemCount, "
        + "t1.itemPrice = :itemPrice, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.orderDetailId = :orderDetailId "
        + "AND t1.delFlag = 0 ")
    void updateItemDetails(@Param("storeId") String storeId,
        @Param("orderDetailId") Integer orderDetailId,
        @Param("itemCount") Integer itemCount,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
