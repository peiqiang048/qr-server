package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OReservate;
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
 * 予約テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OReservateRepository extends JpaRepository<OReservate, Long> {

    /**
     * 予約一覧情報取得.
     *
     * @param storeId           店舗ID
     * @param customerName      顧客名前
     * @param telNumber         電話番号
     * @param status            状態
     * @param reservateTimeFrom 開始日付
     * @param reservateTimeTo   終了日付
     * @param pageable          ページ情報
     * @return 予約一覧情報
     */
    @Query(value =
        "SELECT t1.reservate_id AS reservateId,                                             "
            + "                TO_CHAR(t1.reservate_time, 'YYYY-MM-DD HH24:MI:SS') AS reservateTime,   "
            + "                t1.customer_name AS customerName,                                       "
            + "                t1.tel_number AS telNumber,                                             "
            + "                t1.customer_count AS customerCount,                                     "
            + "                CASE WHEN t3.courseCount > 0 AND t5.buffetCount > 0 THEN 'コース+放題'              "
            + "                     WHEN t3.courseCount > 0 THEN 'コース'                            "
            + "                     WHEN t5.buffetCount > 0 THEN '放題'                              "
            + "                     ELSE '' END AS courseBuffet,                                       "
            + "                t1.comment AS comment,                                                  "
            + "                t1.status AS status,                                                     "
            + "                t7.table_name AS tableName                                                     "
            + "         FROM o_reservate t1                                                            "

            + "         LEFT JOIN (SELECT rt1.store_id ,  rt1.reservate_id , min(rt2.code) as tableId "
            + "                    from o_reservate rt1 "
            + "                    INNER JOIN r_reservate rt2 "
            + "                    ON rt1.store_id = rt2.store_id "
            + "                    and rt2.classification = '03' "
            + "                    and rt2.reservate_id = rt1.reservate_id "
            + "                    and rt2.store_id=:storeId "
            + "                    and rt2.del_flag = 0 "
            + "                    where rt1.del_flag = 0   "
            + "                    GROUP BY rt1.store_id ,  rt1.reservate_id) t6 "
            + "         ON t6.store_id = t1.store_id "
            + "         AND t6.reservate_id = t1.reservate_id "
            + "         LEFT JOIN m_table t7 "
            + "         ON t7.store_id = t6.store_id "
            + "         AND t7.table_id = t6.tableId"
            + "         AND t7.del_flag = 0  "

            + "         LEFT JOIN (                                                                    "
            + "             SELECT t2.store_id AS storeId,                                             "
            + "                    t2.reservate_id AS reservateId,                                     "
            + "                    COUNT(t2) AS courseCount                                            "
            + "             FROM r_reservate t2                                                        "
            + "             WHERE t2.classification = '01'                                             "
            + "             AND t2.del_flag = 0                                                        "
            + "             GROUP BY t2.store_id, t2.reservate_id                                      "
            + "         ) t3                                                                           "
            + "         ON t3.storeId = t1.store_id                                                    "
            + "         AND t3.reservateId = t1.reservate_id                                           "
            + "         LEFT JOIN (                                                                    "
            + "             SELECT t4.store_id AS storeId,                                             "
            + "                    t4.reservate_id AS reservateId,                                     "
            + "                    COUNT(t4) AS buffetCount                                            "
            + "             FROM r_reservate t4                                                        "
            + "             WHERE t4.classification = '02'                                             "
            + "             AND t4.del_flag = 0                                                        "
            + "             GROUP BY t4.store_id, t4.reservate_id                                      "
            + "         ) t5                                                                           "
            + "         ON t5.storeId = t1.store_id                                                    "
            + "         AND t5.reservateId = t1.reservate_id                                           "
            + "         WHERE t1.store_id = :storeId                                                   "
            + "         AND (t1.customer_name LIKE  '%'||:customerName||'%' OR :customerName = '')                   "
            + "         AND (t1.tel_number LIKE  '%'||:telNumber||'%' OR :telNumber = '')                            "
            + "         AND (t1.status = :status OR :status = '')                                      "
            + "         AND (TO_TIMESTAMP(:reservateTimeFrom, 'YYYY-MM-DD HH24:MI:SS') <=              "
            + "         t1.reservate_time OR :reservateTimeFrom = '')                                  "
            + "         AND (TO_TIMESTAMP(:reservateTimeTo, 'YYYY-MM-DD HH24:MI:SS') >=                "
            + "         t1.reservate_time OR :reservateTimeTo = '')                                    "
            + "         AND t1.del_flag = 0                                                            "
            + "         ORDER BY t1.reservate_time ASC, t1.reservate_id ASC ", nativeQuery = true)
    Page<Map<String, Object>> findReservateInfoByStoreId(
        @Param("storeId") String storeId, @Param("customerName") String customerName,
        @Param("telNumber") String telNumber, @Param("status") String status,
        @Param("reservateTimeFrom") String reservateTimeFrom,
        @Param("reservateTimeTo") String reservateTimeTo, Pageable pageable);

    /**
     * 指定予約IDの予約情報取得.
     *
     * @param storeId     店舗ID
     * @param delFlag     削除フラグ
     * @param reservateId 予約ID
     * @return 予約情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OReservate findByStoreIdAndDelFlagAndReservateId(String storeId, Integer delFlag,
        Integer reservateId);

    /**
     * 状態変更処理.
     *
     * @param storeId     店舗ID
     * @param reservateId 予約ID
     * @param status      状態
     * @param userId      更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE OReservate "
        + "SET status = :status, "
        + "updOperCd = :userId, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND reservateId = :reservateId "
        + "AND delFlag = 0 ")
    void updateStatus(@Param("storeId") String storeId,
        @Param("reservateId") Integer reservateId,
        @Param("status") String status,
        @Param("userId") String userId,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 予約変更処理.
     *
     * @param storeId       店舗ID
     * @param reservateId   予約ID
     * @param reservateTime 来店日時
     * @param useTime       利用時間
     * @param customerName  顧客名前
     * @param telNumber     電話番号
     * @param customerCount 顧客人数
     * @param reservateType 予約区分
     * @param userId        更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OReservate "
        + "SET reservateTime = :reservateTime, "
        + "useTime = :useTime, "
        + "customerName = :customerName, "
        + "telNumber = :telNumber, "
        + "customerCount = :customerCount, "
        + "reservateType = :reservateType, "
        + "comment = :comment, "
        + "updOperCd = :userId, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND reservateId = :reservateId "
        + "AND delFlag = 0 ")
    void updateReservate(@Param("storeId") String storeId,
        @Param("reservateId") Integer reservateId,
        @Param("reservateTime") ZonedDateTime reservateTime,
        @Param("useTime") BigDecimal useTime,
        @Param("customerName") String customerName,
        @Param("telNumber") String telNumber,
        @Param("customerCount") Integer customerCount,
        @Param("reservateType") String reservateType,
        @Param("comment") String comment,
        @Param("userId") String userId,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 席一覧情報取得.
     *
     * @param storeId        店舗ID
     * @param reservateTime  来店日時
     * @param useTime        利用時間
     * @param groupCode      テーブル区分
     * @param defaultUseTime デフォルト利用時間
     * @return 席一覧情報
     */
    @Query(value = "SELECT t7.tableId,                                                             "
        + "            t7.tableName,                                                           "
        + "            t7.tableColor,                                                          "
        + "            t7.areaId,                                                              "
        + "            t7.areaName,                                                            "
        + "            t7.tableSeatCount,                                                      "
        + "            CASE WHEN SUM(t7.useFlag) > 0 THEN '2' else '1' END useFlag             "
        + "     FROM                                                                           "
        + "     (                                                                              "
        + "         SELECT t1.table_id AS tableId,                                             "
        + "                t1.table_name AS tableName,                                         "
        + "                SUBSTR(t6.code_name, strpos(t6.code_name, ',') + 1) AS tableColor,  "
        + "                t2.table_index_id AS areaId,                                        "
        + "                t2.area_name AS areaName,                                           "
        + "                t1.table_seat_count tableSeatCount,                                 "
        + "                CASE WHEN t5.tableId IS null THEN 0 ELSE 1 END AS useFlag           "
        + "         FROM m_table t1                                                            "
        + "         INNER JOIN m_table_index t2                                                "
        + "         ON t2.store_id = t1.store_id                                               "
        + "         AND t2.table_index_id = t1.table_index_id                                  "
        + "         AND t2.del_flag = '0'                                                      "
        + "         INNER JOIN m_code t6                                                       "
        + "         ON t6.store_id = :storeId                                                  "
        + "         AND t6.code_group = :groupCode                                             "
        + "         AND t6.code = t1.table_type                                                "
        + "         AND t6.del_flag = '0'                                                      "
        + "         Left JOIN (                                                                "
        + "             SELECT t4.code AS tableId                                              "
        + "             FROM o_reservate t3                                                    "
        + "             LEFT JOIN r_reservate t4                                               "
        + "             ON t4.store_id = t3.store_id                                           "
        + "             AND t4.reservate_id = t3.reservate_id                                  "
        + "             AND t4.classification = '03'                                           "
        + "             AND t4.del_flag = '0'                                                  "
        + "             WHERE                                                                  "
        + "             (                                                                      "
        + "                 (t3.reservate_time >=                                              "
        + "                       to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS')        "
        + "              AND t3.reservate_time <=                                              "
        + "                       to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS') +      "
        + "                       :useTime * interval '1 hours')                               "
        + "              OR (t3.reservate_time + t3.use_time * interval '1 hours' >=           "
        + "                       to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS')        "
        + "              AND t3.reservate_time + t3.use_time * interval '1 hours' <=           "
        + "                       to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS') +      "
        + "                       :useTime * interval '1 hours')                               "
        + "             )                                                                      "
        + "             AND t3.status = '01'                                                   "
        + "             AND t3.del_flag = '0'                                                  "
        + "         ) t5                                                                       "
        + "         ON t5.tableId = t1.table_id                                                "
        + "         WHERE t1.store_id = :storeId                                               "
        + "         AND t1.del_flag = '0'                                                      "
        + "         UNION ALL                                                                  "
        + "         SELECT t1.table_id AS tableId ,                                            "
        + "                t1.table_name AS tableName,                                         "
        + "                SUBSTR(t4.code_name, strpos(t4.code_name, ',') + 1) AS tableColor,  "
        + "                t2.table_index_id AS areaId,                                        "
        + "                t2.area_name AS areaName,                                           "
        + "                t1.table_seat_count tableSeatCount,                                 "
        + "                CASE WHEN t3.order_summary_id IS null THEN 0 ELSE 1 END AS useFlag  "
        + "         FROM m_table t1                                                            "
        + "         INNER JOIN m_table_index t2                                                "
        + "         ON t2.store_id = t1.store_id                                               "
        + "         AND t2.table_index_id = t1.table_index_id                                  "
        + "         AND t2.del_flag = '0'                                                      "
        + "         INNER JOIN m_code t4                                                       "
        + "         ON t4.store_id = :storeId                                                  "
        + "         AND t4.code_group = :groupCode                                             "
        + "         AND t4.code = t1.table_type                                                "
        + "         AND t4.del_flag = '0'                                                      "
        + "         LEFT JOIN o_order_summary t3                                               "
        + "         ON t3.table_id = t1.table_id                                               "
        + "         AND (                                                                      "
        + "              (t3.ins_date_time >=                                                  "
        + "                   to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS')            "
        + "           AND t3.ins_date_time <=                                                  "
        + "                   to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS') +          "
        + "                   :useTime * interval '1 hours')                                   "
        + "           OR (t3.ins_date_time + :defaultUseTime * interval '1 hours' >=           "
        + "                   to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS')            "
        + "           AND t3.ins_date_time + :defaultUseTime * interval '1 hours' <=           "
        + "                   to_timestamp(:reservateTime, 'YYYY-MM-DD HH24:MI:SS') +          "
        + "                   :useTime * interval '1 hours')                                   "
        + "         )                                                                          "
        + "         AND t3.seat_release = '01'                                                 "
        + "         AND t3.del_flag = '0'                                                      "
        + "         WHERE t1.store_id = :storeId                                               "
        + "         AND t1.del_flag = '0'                                                      "
        + "     ) t7                                                                           "
        + "     GROUP BY t7.tableId,                                                           "
        + "              t7.tableName,                                                         "
        + "              t7.tableColor,                                                        "
        + "              t7.areaId,                                                            "
        + "              t7.areaName,                                                          "
        + "              t7.tableSeatCount                                                     "
        + "     ORDER BY t7.areaId, t7.tableId", nativeQuery = true)
    List<Map<String, Object>> findAllTableInfoByStoreIdAndReservateTimeAndUseTime(
        @Param("storeId") String storeId, @Param("reservateTime") String reservateTime,
        @Param("useTime") BigDecimal useTime, @Param("groupCode") String groupCode,
        @Param("defaultUseTime") BigDecimal defaultUseTime);

    /**
     * 予約情報取得.
     *
     * @param storeId     店舗ID
     * @param reservateId 予約ID
     * @return 予約情報
     */
    @Query(value = "SELECT t1.reservate_time AS reservateTime, "
        + "t1.use_time AS useTime, "
        + "t1.customer_name AS customerName, "
        + "t1.tel_number AS telNumber, "
        + "t1.customer_count AS customerCount, "
        + "t1.reservate_type AS reservateType, "
        + "t1.comment AS comment, "
        + "t1.status AS status "
        + "FROM o_reservate t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reservate_id = :reservateId "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findReservateInfoByStoreIdAndReservateId(
        @Param("storeId") String storeId, @Param("reservateId") Integer reservateId);
}
