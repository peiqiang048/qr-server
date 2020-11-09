package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OCall;
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
 * 呼出テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OCallRepository extends JpaRepository<OCall, Long> {

    /**
     * 指定受付IDの呼出情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param delFlag       削除フラグ
     * @return 呼出情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OCall findByStoreIdAndDelFlagAndReceivablesId(String storeId, Integer delFlag,
        String receivablesId);

    /**
     * 呼出情報リスト取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 呼出情報リスト
     */
    List<OCall> findByStoreIdAndDelFlagOrderByCallIdDesc(String storeId, Integer delFlag);

    /**
     * 受付テーブル情報取得(登録).
     */
    @Modifying
    @Query(value = ""
        + "INSERT INTO o_call("
        + "store_id,"
        + "call_id,"
        + "receivables_id,"
        + "call_number,"
        + "call_status,"
        + "table_id,"
        + "del_flag,"
        + "ins_oper_cd,"
        + "ins_date_time,"
        + "upd_oper_cd,"
        + "upd_date_time,"
        + "version)"
        + "VALUES( "
        + ":storeId,"
        + ":callId,"
        + ":receivablesId,"
        + ":callNumber,"
        + ":callStatus,"
        + ":tableId,"
        + ":delFlag,"
        + ":insOperCd,"
        + ":insDateTime,"
        + ":updOperCd,"
        + ":updDateTime,"
        + ":version)"
        + "ON conflict(store_id,receivables_id) "
        + "DO UPDATE SET call_status = :callStatus,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime,"
        + "call_number =o_call.call_number + 1,"
        + "version=o_call.version + 1  ", nativeQuery = true)
    void insertOrUpdate(@Param("storeId") String storeId,
        @Param("callId") Integer callId,
        @Param("receivablesId") String receivablesId,
        @Param("callNumber") Integer callNumber,
        @Param("callStatus") String callStatus,
        @Param("tableId") Integer tableId,
        @Param("delFlag") Integer delFlag,
        @Param("insOperCd") String insOperCd,
        @Param("insDateTime") ZonedDateTime insDateTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("version") Integer version);

    /**
     * 呼出中情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesNo 受付No
     * @return 呼出中情報
     */
    @Query(value = "SELECT t4.table_name AS tableName, "
        + "t2.reception_no AS receivablesNo, "
        + "t2.receivables_id AS receivablesId, "
        + "TO_CHAR(t1.ins_date_time, 'yyyy/MM/dd hh24:mi:ss') AS callDateTime, "
        + "t1.call_number AS callCount "
        + "FROM o_call t1 "
        + "INNER JOIN o_receivables t2 "
        + "ON t2.store_id = t1.store_id "
        + "AND t2.receivables_id = t1.receivables_id "
        + "LEFT JOIN o_order_summary t3 "
        + "ON t3.store_id = t1.store_id "
        + "AND t3.receivables_id = t1.receivables_id "
        + "INNER JOIN m_table t4 "
        + "ON t4.store_id = t1.store_id "
        + "AND t4.table_id = t1.table_id "
        + "WHERE t1.store_id = :storeId "
        + "AND (t2.reception_no = :receivablesNo OR :receivablesNo = -1) "
        + "AND t1.call_status = '01' "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "ORDER BY t1.ins_date_time ASC, t1.call_number DESC", nativeQuery = true)
    List<Map<String, Object>> getCallList(@Param("storeId") String storeId,
        @Param("receivablesNo") Integer receivablesNo);

    /**
     * 呼出状態変更処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param userId        更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OCall "
        + "SET callStatus = '02', "
        + "updOperCd = :userId, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE delFlag = 0 "
        + "AND receivablesId = :receivablesId "
        + "AND storeId = :storeId ")
    void updateCallStatus(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("userId") String userId,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
