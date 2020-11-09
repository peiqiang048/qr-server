package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OCall;
import com.cnc.qr.common.entity.OOrder;
import com.cnc.qr.common.entity.OReservate;
import com.cnc.qr.common.entity.RReservate;

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
 * 予約関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RReservateRepository extends JpaRepository<RReservate, Long> {

    /**
     * 指定予約関連データ削除.
     *
     * @param storeId     店舗ID
     * @param reservateId 受付ID
     */
    @Modifying
    @Query(value = "DELETE FROM r_reservate t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reservate_id = :reservateId "
        + "AND del_flag = 0 ", nativeQuery = true)
    void deleteByStoreIdAndReservateId(@Param("storeId") String storeId,
        @Param("reservateId") Integer reservateId);

    /**
     * 予約関連情報（コース）取得.
     *
     * @param storeId     店舗ID
     * @param reservateId 受付ID
     * @return コース情報
     */
    @Query(value = "SELECT t1.code AS courseId, "
        + "t1.count AS courseCount "
        + "FROM r_reservate t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reservate_id = :reservateId "
        + "AND t1.classification = '01' "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.code ASC ", nativeQuery = true)
    List<Map<String, Object>> findCourseInfoByStoreIdAndReservateId(
        @Param("storeId") String storeId, @Param("reservateId") Integer reservateId);

    /**
     * 予約関連情報（放題）取得.
     *
     * @param storeId     店舗ID
     * @param reservateId 受付ID
     * @return 放題情報
     */
    @Query(value = "SELECT t1.code AS buffetId, "
        + "t1.count AS buffetCount "
        + "FROM r_reservate t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reservate_id = :reservateId "
        + "AND t1.classification = '02' "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.code ASC ", nativeQuery = true)
    List<Map<String, Object>> findBuffetInfoByStoreIdAndReservateId(
        @Param("storeId") String storeId, @Param("reservateId") Integer reservateId);

    /**
     * 予約関連情報（席）取得.
     *
     * @param storeId     店舗ID
     * @param reservateId 受付ID
     * @return 席情報
     */
    @Query(value = "SELECT t1.code AS tableId "
        + "FROM r_reservate t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.reservate_id = :reservateId "
        + "AND t1.classification = '03' "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.code ASC ", nativeQuery = true)
    List<Map<String, Object>> findTableInfoByStoreIdAndReservateId(
        @Param("storeId") String storeId, @Param("reservateId") Integer reservateId);
}
