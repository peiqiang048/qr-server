package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MReceipt;
import com.cnc.qr.common.shared.model.PrintDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 伝票マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MReceiptRepository extends JpaRepository<MReceipt, Long> {

    /**
     * 伝票プリンター情報取得.
     *
     * @param storeId   店舗ID
     * @param receiptId 伝票ID
     * @return 伝票プリンター情報
     */
    @Query(value = "select new com.cnc.qr.common.shared.model.PrintDto(" +
        "t3.printIp," +
        "t3.bluetoothName," +
        "t3.brandCode, t3.connectionMethodCode, t3.printSize )" +
        "from MReceipt t1 " +
        "INNER JOIN RReceiptPrint t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.receiptId = t2.receiptId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN MPrint t3 " +
        "ON t3.storeId = t2.storeId " +
        "AND t2.printId = t3.printId " +
        "AND t3.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.receiptId = :receiptId " +
        "AND t1.delFlag = 0 "
    )
    PrintDto getPrintInfo(
        @Param("storeId") String storeId,
        @Param("receiptId") Integer receiptId);

    /**
     * レシート一覧情報取得.
     *
     * @param storeId 店舗ID
     * @return レシート一覧
     */
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY t1.receipt_id ASC) AS num," +
        "t1.receipt_id AS receiptId, " +
        "t1.receipt_name AS receiptName " +
        "FROM m_receipt t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findReceiptInfoList(@Param("storeId") String storeId);

    /**
     * レシート情報取得.
     *
     * @param storeId   店舗ID
     * @param receiptId 伝票ID
     * @return レシート情報
     */
    @Query(value = "SELECT " +
        "t1.receipt_name AS receiptName, " +
        "t2.print_id AS printId  " +
        "FROM m_receipt t1 " +
        "LEFT JOIN r_receipt_print t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.receipt_id = t2.receipt_id " +
        "AND t2.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.receipt_id = :receiptId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findReceiptInfo(@Param("storeId") String storeId,
        @Param("receiptId") Integer receiptId);
}
