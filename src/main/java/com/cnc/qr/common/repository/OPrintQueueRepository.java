package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OPrintQueue;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 印刷テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OPrintQueueRepository extends JpaRepository<OPrintQueue, Long> {

    /**
     * 印刷注文情報取得.
     *
     * @param storeId 店舗ID
     * @param code    印刷状態
     * @param delFlag 削除フラグ
     * @return 印刷注文情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<OPrintQueue> findByStoreIdAndPrintStatusAndDelFlag(String storeId, String code,
        Integer delFlag);

    /**
     * 印刷状態更新処理.
     *
     * @param storeId     店舗ID
     * @param unPrint     未印刷
     * @param printing    印刷中
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update o_print_queue "
        + "set print_status = :printing, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where store_id = :storeId "
        + "and print_status = :unPrint "
        + "and del_flag = 0 ", nativeQuery = true)
    void updatePrintStatus(@Param("storeId") String storeId,
        @Param("unPrint") String unPrint,
        @Param("printing") String printing,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 印刷状態変更.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param printStatus    印刷状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "update o_print_queue "
        + "set print_status = :printStatus, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where store_id = :storeId "
        + "and order_summary_id = :orderSummaryId "
        + "and order_id = :orderId "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateOrderPrintStatus(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") String orderId,
        @Param("printStatus") String printStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
