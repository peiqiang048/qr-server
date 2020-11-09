package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MTable;
import com.cnc.qr.core.order.model.TableDto;
import com.cnc.qr.core.order.model.TableListDto;
import com.cnc.qr.core.order.model.TableSeatDto;
import com.cnc.qr.core.pc.model.TableInfoDto;
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
 * テーブルマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MTableRepository extends JpaRepository<MTable, Long> {

    /**
     * テーブルリスト得処理.
     *
     * @param storeId         店舗ID
     * @param areaId          エリアID
     * @param itemStatus      商品状態
     * @param seatRelease     席解除フラグ
     * @param classification  予約関連分類名
     * @param reservateStatus 予約状態
     * @param dateTime        日時
     * @param defaultUseTime  ディフォルト利用時間
     * @return テーブルリスト
     */
    @Query(value = "SELECT t3.tableId, "
        + "t3.tableName, "
        + "t3.tableSeatCount, "
        + "t3.customerCount, "
        + "t3.orderCount, "
        + "t3.orderStatus, "
        + "t3.price, "
        + "case when t3.seatRelease = '0' and t3.payStatus = '01' then '05' else t3.orderType end orderType, "
        + "count(t4.reservate_id) as reservateCount "
        + "FROM( "
        + "SELECT t1.store_id as storeId, "
        + "t1.table_id AS tableId, "
        + "t1.table_name AS tableName, "
        + "t1.table_seat_count AS tableSeatCount, "
        + "SUM(COALESCE(t2.customerCount, 0)) AS customerCount, "
        + "COUNT(t2.orderType) AS orderCount, "
        + "CASE WHEN 0 < SUM(t2.itemStatus) THEN '01' ELSE '02' END AS orderStatus, "
        + "SUM(COALESCE(t2.orderAmount, 0)) AS price,"
        + "MIN(t2.orderType) AS orderType, max(t2.seat_release ) as seatRelease,max(t2.pay_status ) as payStatus "
        + "FROM m_table t1 "
        + "LEFT OUTER JOIN ("
        + "  SELECT"
        + "     tt1.store_id, "
        + "     tt1.order_summary_id,"
        + "     MAX(tt1.table_id) as tableId,"
        + "     MAX(tt1.customer_count) as customerCount,"
        + "     MAX(tt1.order_amount) as orderAmount,"
        + "     MIN(tt2.order_type) AS orderType,"
        + "     COUNT(tt3.store_id) AS itemStatus,"
        + "     tt1.seat_release ,tt2.pay_status "
        + "  FROM o_order_summary tt1"
        + "  INNER JOIN o_order tt2 "
        + "  ON tt1.store_id = tt2.store_id"
        + "  AND tt1.order_summary_id = tt2.order_summary_id"
        + "  LEFT OUTER JOIN o_order_details tt3 "
        + "  ON tt2.store_id = tt3.store_id"
        + "  AND tt2.order_id = tt3.order_id"
        + "  AND tt3.del_flag = 0"
        + "  AND tt3.item_status = :itemStatus"
        + "  WHERE tt1.del_flag = 0"
        + "  AND tt2.del_flag = 0"
        + "  AND tt1.seat_release = :seatRelease"
        + "  AND tt1.store_id = :storeId"
        + "  GROUP BY tt1.store_id, tt1.order_summary_id,tt1.seat_release,tt2.pay_status "
        + "  UNION ALL "
        + "      SELECT"
        + "         ttt1.store_id,"
        + "         ttt1.order_summary_id,"
        + "         MAX(ttt1.table_id) as tableId,"
        + "         sum(ttt1.customer_count) as customerCount,"
        + "         0 as orderAmount,"
        + "         '01' as orderType,"
        + "         null AS itemStatus,ttt1.seat_release ,'02' as pay_status"
        + "      FROM o_order_summary ttt1"
        + "      WHERE ttt1.del_flag = 0"
        + "      AND ttt1.store_id = :storeId"
        + "      AND ttt1.table_id is not null"
        + "  AND ttt1.seat_release = :seatRelease"
        + "      AND ttt1.order_amount = 0"
        + "      AND NOT EXISTS("
        + "         SELECT *"
        + "         FROM o_order ttt2 WHERE ttt2.order_summary_id = ttt1.order_summary_id AND ttt2.store_id = :storeId"
        + "         )"
        + "         GROUP BY ttt1.store_id, ttt1.order_summary_id,ttt1.seat_release"
        + ") t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.table_id = t2.tableId "
        + "WHERE t1.store_id = :storeId "
        + "AND (t1.table_index_id = :areaId OR :areaId = -1) "
        + "AND t1.del_flag = 0 "
        + "GROUP BY t1.store_id, t1.table_id, t1.table_name, t1.table_seat_count "
        + "ORDER BY tableId ASC )t3 "
        + "LEFT JOIN "
        + "(SELECT res.store_id,res.reservate_id,rres.code "
        + "FROM o_reservate res,r_reservate rres "
        + "WHERE res.store_id = rres.store_id "
        + "AND res.reservate_id = rres.reservate_id "
        + "AND res.reservate_time > :dateTime "
        + "AND res.reservate_time - :dateTime < (:defaultUseTime * interval '1 hours') "
        + "AND res.status = :reservateStatus "
        + "AND res.del_flag = 0 "
        + "AND rres.del_flag = 0 "
        + "AND rres.classification = :classification) t4 "
        + "ON t3.storeId = t4.store_id "
        + "AND t3.tableId = t4.code "
        + "GROUP BY t3.tableId,t3.tableName,t3.tableSeatCount, "
        + "t3.customerCount,t3.orderCount,t3.orderStatus,t3.price,"
        + "t3.orderType,t3.seatRelease,t3.payStatus ", nativeQuery = true)
    List<Map<String, Object>> findTableListByAreaId(@Param("storeId") String storeId,
        @Param("areaId") Integer areaId, @Param("itemStatus") String itemStatus,
        @Param("seatRelease") String seatRelease, @Param("classification") String classification,
        @Param("reservateStatus") String reservateStatus,
        @Param("dateTime") ZonedDateTime dateTime,
        @Param("defaultUseTime") BigDecimal defaultUseTime);


    /**
     * テーブルリスト得処理.
     *
     * @param storeId     店舗ID
     * @param areaId      エリアID
     * @param payStatus   支払状態
     * @param seatRelease 席解除フラグ
     * @param paymentType 予約関連分類名
     * @return テーブルリスト
     */
    @Query(value = "SELECT "
        + "	t1.table_id as tableId, "
        + "	t1.table_name as tableName, "
        + "	t1.table_seat_count as tableSeatCount, "
        + "	count( t4.customer_count ) AS customerCount, "
        + "	count( t4.order_summary_id ) AS orderCount , "
        + "	sum( t4.order_amount ) AS price,  "
        + "	'05' AS orderType  "
        + "FROM "
        + "	m_table t1 "
        + "	JOIN ( "
        + "SELECT DISTINCT "
        + "	t2.order_summary_id, "
        + "	t2.table_id, "
        + "	t2.customer_count, "
        + "	t2.order_amount  "
        + "FROM "
        + "	o_order_summary t2 "
        + "	JOIN o_order t3 ON t3.store_id = t2.store_id  "
        + "	AND t3.del_flag = 0  "
        + "	AND t3.pay_status = :payStatus "
        + "WHERE "
        + "	t2.seat_release = :seatRelease  "
        + "	AND t2.del_flag = 0  "
        + "	AND t2.store_id = :storeId  "
        + "	AND payment_type = :paymentType  "
        + "	) t4 ON t4.table_id = t1.table_id  "
        + "	AND t1.store_id = :storeId  "
        + "	AND t1.del_flag = 0  "

        + "	where  t1.table_index_id = :areaId "
        + "GROUP BY "
        + "	t1.table_id, "
        + "	t1.table_name, "
        + "	t1.table_seat_count ", nativeQuery = true)
    List<Map<String, Object>> findTableListByAreaIdAndAdvancePayment(
        @Param("storeId") String storeId,
        @Param("areaId") Integer areaId, @Param("payStatus") String payStatus,
        @Param("seatRelease") String seatRelease,
        @Param("paymentType") String paymentType);

    /**
     * テーブル情報取得.
     *
     * @param storeId 店舗ID
     * @param tableId テーブルID
     * @return テーブル情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.TableDto("
        + "t1.tableName, "
        + "t2.areaName, "
        + "t1.tableSeatCount) "
        + "FROM MTable t1 "
        + "INNER JOIN MTableIndex t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.tableIndexId = t2.tableIndexId "
        + "AND t2.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableId = :tableId "
        + "AND t1.delFlag = 0 ")
    TableDto findTableByTableId(@Param("storeId") String storeId,
        @Param("tableId") Integer tableId);

    /**
     * 全てテーブルリスト得処理.
     *
     * @param storeId         店舗ID
     * @param payStatus       支払状態
     * @param classification  予約関連分類名
     * @param reservateStatus 予約状態
     * @param dateTime        日時
     * @param defaultUseTime  ディフォルト利用時間
     * @return テーブルリスト
     */
    @Query(value = "select t4.tableId, "
        + "t4.tableName, "
        + "t4.tableSeatCount, "
        + "t4.orderSummaryId, "
        + "t4.customerCount, "
        + "count(t5.reservate_id) as reservateCount "
        + "from("
        + "select t1.store_id AS storeId, "
        + "t1.table_id AS tableId, "
        + "t1.table_name AS tableName, "
        + "COALESCE(t1.table_seat_count, 0) AS tableSeatCount, "
        + "t2.order_summary_id AS orderSummaryId, "
        + "COALESCE(t2.customer_count,0) AS customerCount "
        + "from m_table t1 "
        + "left join ("
        + "    select "
        + "    tt1.order_summary_id, "
        + "    tt1.customer_count, "
        + "        tt1.table_id "
        + "    from o_order_summary tt1 "
        + "    INNER join o_order tt2 "
        + "    on tt2.order_summary_id = tt1.order_summary_id "
        + "    and tt2.store_id = tt1.store_id "
        + "    and tt2.pay_status = :payStatus "
        + "    and tt2.del_flag = 0"
        + "    where tt1.store_id = :storeId "
        + "    and tt1.del_flag = 0 GROUP BY tt1.order_summary_id,tt1.customer_count,tt1.table_id "
        + "    UNION ALL "
        + "    select "
        + "    tt3.order_summary_id, "
        + "    tt3.customer_count, "
        + "        tt3.table_id "
        + "    from o_order_summary tt3 "
        + "    where tt3.store_id = :storeId "
        + "    and tt3.del_flag = 0 and tt3.order_amount = 0 "
        + "GROUP BY tt3.order_summary_id,tt3.customer_count,tt3.table_id "
        + ") t2 "
        + "on t2.table_id = t1.table_id "
        + "where t1.store_id = :storeId "
        + "and t1.del_flag = 0 ) t4 "
        + "LEFT JOIN "
        + "(SELECT res.store_id,res.reservate_id,rres.code "
        + "FROM o_reservate res,r_reservate rres "
        + "WHERE res.store_id = rres.store_id "
        + "AND res.reservate_id = rres.reservate_id "
        + "AND res.reservate_time > :dateTime "
        + "AND res.reservate_time - :dateTime < (:defaultUseTime * interval '1 hours') "
        + "AND res.status = :reservateStatus "
        + "AND res.del_flag = 0 "
        + "AND rres.del_flag = 0 "
        + "AND rres.classification = :classification) t5 "
        + "ON t4.storeId = t5.store_id "
        + "AND t4.tableId = t5.code "
        + "GROUP BY t4.tableId,t4.tableName,t4.tableSeatCount,"
        + "t4.orderSummaryId,t4.customerCount ", nativeQuery = true)
    List<Map<String, Object>> findChooseAbleTable(@Param("storeId") String storeId,
        @Param("payStatus") String payStatus, @Param("classification") String classification,
        @Param("reservateStatus") String reservateStatus, @Param("dateTime") ZonedDateTime dateTime,
        @Param("defaultUseTime") BigDecimal defaultUseTime);

    /**
     * テーブル一覧取得処理.
     *
     * @param storeId  店舗ID
     * @param pageable ページ情報
     * @return テーブル一覧
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.TableInfoDto("
        + "t1.tableId, "
        + "t1.tableName, "
        + "t2.areaName) "
        + "FROM MTable t1 "
        + "LEFT JOIN MTableIndex t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.tableIndexId = t2.tableIndexId "
        + "AND t2.delFlag = 0  "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.delFlag = 0 "
        + "ORDER BY t1.tableId ASC ")
    Page<TableInfoDto> findTableInfoByStoreId(@Param("storeId") String storeId, Pageable pageable);

    /**
     * テーブル情報取得処理.
     *
     * @param storeId 店舗ID
     * @param tableId テーブルID
     * @param delFlag 削除フラグ
     * @return テーブル情報
     */
    MTable findByStoreIdAndTableIdAndDelFlag(String storeId, Integer tableId, Integer delFlag);

    /**
     * 指定テーブルIDのテーブル情報ロック.
     *
     * @param storeId 店舗ID
     * @param tableId テーブルID
     * @return テーブル情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MTable t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableId = :tableId "
        + "AND t1.delFlag = 0")
    MTable findByTableIdForLock(@Param("storeId") String storeId,
        @Param("tableId") Integer tableId);

    /**
     * 指定テーブルID削除.
     *
     * @param storeId     店舗ID
     * @param tableIdList テーブルIDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MTable t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableId IN :tableIdList ")
    void updateDelFlagByTableId(@Param("storeId") String storeId,
        @Param("tableIdList") List<Integer> tableIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * テーブル一覧取得処理.
     *
     * @param storeId 店舗ID
     * @param areaId  エリアID
     * @return テーブル一覧
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.TableListDto("
        + "t1.tableId, "
        + "t1.tableName) "
        + "FROM MTable t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.delFlag = 0 "
        + "AND t1.tableIndexId = :areaId "
        + "ORDER BY t1.tableId ASC ")
    List<TableListDto> getTableList(@Param("storeId") String storeId,
        @Param("areaId") Integer areaId);

    /**
     * テーブル情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return テーブル情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.TableListDto("
        + "t1.tableId, "
        + "t2.tableName) "
        + "FROM OOrderSummary t1 "
        + "INNER JOIN MTable t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.tableId = t2.tableId  "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.receivablesId = :receivablesId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 ")
    TableListDto getTableInfoByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * テーブル情報取得.
     *
     * @param storeId     店舗ID
     * @param tableIdList テーブルIDリスト
     * @return テーブル情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.TableSeatDto("
        + "t1.tableId, "
        + "t1.tableSeatCount) "
        + "FROM MTable t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableId in :tableIdList "
        + "AND t1.delFlag = 0 ")
    List<TableSeatDto> getTableSeatList(@Param("storeId") String storeId,
        @Param("tableIdList") List<Integer> tableIdList);


    /**
     * テーブル一覧取得処理.
     *
     * @param storeId  店舗ID
     * @param pageable ページ情報
     * @return テーブル一覧
     */
    @Query(value = "SELECT "
        + "t1.table_id, "
        + "t1.table_name, "
        + "t2.area_name,"
        + "ROW_NUMBER() OVER(ORDER BY t1.table_id ASC) AS num  "
        + "FROM m_table t1 "
        + "LEFT JOIN m_table_index t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.table_index_id = t2.table_index_id "
        + "AND t2.del_flag = 0  "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.table_id ASC ", nativeQuery = true)
    Page<Map<String, Object>> findStoreIdTableInfo(@Param("storeId") String storeId,
        Pageable pageable);
}
