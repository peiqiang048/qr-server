package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RKitchenPrint;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * キチンプリンタ関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RKitchenPrintRepository extends JpaRepository<RKitchenPrint, Long> {

    /**
     * キチンプリンタ関連テーブル取得.
     *
     * @param storeId   店舗ID
     * @param kitchenId キチンID
     * @param printId   プリンタID
     * @param delFlag   削除フラグ
     * @return 関連データ
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    RKitchenPrint findByStoreIdAndKitchenIdAndPrintIdAndDelFlag(String storeId, Integer kitchenId,
        Integer printId, Integer delFlag);

    /**
     * キチンプリンタ関連情報取得(削除時用).
     *
     * @param storeId     店舗ID
     * @param printIdList プリンタIDリスト
     * @param code        削除フラグ
     * @return 関連データ
     */
    List<RKitchenPrint> findByStoreIdAndPrintIdInAndDelFlag(String storeId,
        List<Integer> printIdList, Integer code);

    /**
     * 指定キッチンプリンタ削除.
     *
     * @param storeId       店舗ID
     * @param kitchenIdList プッチンプリIDリスト
     */
    @Modifying
    @Query(value = "DELETE FROM r_kitchen_print t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.kitchen_id IN :kitchenIdList AND del_flag = 0 ", nativeQuery = true)
    void deleteByStoreIdAndKitchenId(@Param("storeId") String storeId,
        @Param("kitchenIdList") List<Integer> kitchenIdList);


    /**
     * プリンター関連情報削除.
     *
     * @param storeId  店舗ID
     * @param printIds プリンターIDリスト
     */
    @Modifying
    @Query(value = "DELETE FROM r_kitchen_print "
        + "WHERE store_id = :storeId "
        + "AND print_id IN :printIds "
        + "AND del_flag = 0 ", nativeQuery = true)
    void delFlagByprintIdAndKitchenId(@Param("storeId") String storeId,
        @Param("printIds") List<Integer> printIds);

}
