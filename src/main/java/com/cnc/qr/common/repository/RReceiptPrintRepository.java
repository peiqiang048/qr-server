package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RReceiptPrint;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 伝票プリンタ関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RReceiptPrintRepository extends JpaRepository<RReceiptPrint, Long> {

    /**
     * 伝票プリンタ関連情報取得.
     *
     * @param storeId     店舗ID
     * @param printIdList プリンタIDリスト
     * @param code        削除フラグ
     * @return 伝票プリンタ関連情報
     */
    List<RReceiptPrint> findByStoreIdAndPrintIdInAndDelFlag(String storeId,
        List<Integer> printIdList, Integer code);

    /**
     * プリンター関連情報削除.
     *
     * @param storeId  店舗ID
     * @param printIds プリンタIDリスト
     */
    @Modifying
    @Query(value = "delete from r_receipt_print "
        + "WHERE store_id = :storeId "
        + "AND print_id IN :printIds "
        + "AND del_flag = 0  ", nativeQuery = true)
    void delFlagByprintIdAndReceiptId(@Param("storeId") String storeId,
        @Param("printIds") List<Integer> printIds);

    /**
     * プリンター関連情報取得.
     *
     * @param storeId   店舗ID
     * @param receiptId 伝票ID
     * @param delFlag   削除フラグ
     * @return プリンタ関連情報
     */
    List<RReceiptPrint> findByStoreIdAndReceiptIdAndDelFlag(String storeId,
        Integer receiptId, Integer delFlag);

    /**
     * レシートプリンター関連更新.
     *
     * @param storeId     店舗ID
     * @param receiptId   伝票ID
     * @param printId     プリンタID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update r_receipt_print "
        + "set print_id = :printId, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and receipt_id = :receiptId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateReceiptPrint(@Param("storeId") String storeId,
        @Param("receiptId") Integer receiptId,
        @Param("printId") Integer printId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
