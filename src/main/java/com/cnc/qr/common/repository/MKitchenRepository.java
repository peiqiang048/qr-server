package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MKitchen;
import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
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
 * キチンマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MKitchenRepository extends JpaRepository<MKitchen, Long> {

    /**
     * キチン情報取得.
     *
     * @param storeId 店舗ID
     * @return キチン情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetKitchenListOutputDto(" +
        "t1.kitchenId, " +
        "t1.kitchenName) " +
        "from MKitchen t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 ")
    List<GetKitchenListOutputDto> getKitchenList(@Param("storeId") String storeId);

    /**
     * キッチン一覧情報取得.
     *
     * @param storeId   店舗ID
     * @param codeGroup プリンターブランド
     * @return キッチン一覧情報
     */
    @Query(value = "SELECT " +
        "t1.kitchen_id AS kitchenId, " +
        "t1.kitchen_name AS kitchenName, " +
        "t3.print_id AS printId, " +
        "t3.print_name AS printName, " +
        "t4.code_name AS brand, " +
        "t3.print_model_no AS printModelNo " +
        "FROM m_kitchen t1 " +
        "LEFT JOIN r_kitchen_print t2 " +
        "ON t2.store_id = t1.store_id " +
        "AND t2.kitchen_id = t1.kitchen_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_print t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.print_id = t2.print_id " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_code t4 " +
        "ON t4.store_id = t3.store_id " +
        "AND t4.code_group = :codeGroup " +
        "AND t4.code = t3.brand_code " +
        "AND t4.del_flag = 0 " +
        "WHERE  t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findKitchenList(@Param("storeId") String storeId,
        @Param("codeGroup") String codeGroup);

    /**
     * キッチン名称取得.
     *
     * @param storeId   店舗ID
     * @param kitchenId キッチンID
     * @return キッチン名称
     */
    @Query(value = "SELECT " +
        "t1.kitchen_name AS kitchenName " +
        "FROM m_kitchen t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.kitchen_id = :kitchenId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    String findKitchenName(@Param("storeId") String storeId, @Param("kitchenId") Integer kitchenId);

    /**
     * キッチン情報取得ロック.
     *
     * @param storeId   店舗ID
     * @param kitchenId キッチンID
     * @return キッチン情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 " +
        "FROM MKitchen t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.kitchenId = :kitchenId " +
        "AND t1.delFlag = 0 ")
    MKitchen findKitchenForLock(@Param("storeId") String storeId,
        @Param("kitchenId") Integer kitchenId);

    /**
     * キッチン更新.
     *
     * @param storeId     店舗ID
     * @param kitchenId   キッチンID
     * @param kitchenName キッチン名
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     * @return 更新件数
     */
    @Modifying
    @Query(value = ""
        + "UPDATE m_kitchen "
        + "SET kitchen_name = :kitchenName,"
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId "
        + "AND kitchen_id = :kitchenId "
        + "AND del_flag = 0 ", nativeQuery = true)
    Integer updateKitchen(@Param("storeId") String storeId, @Param("kitchenId") Integer kitchenId,
        @Param("kitchenName") String kitchenName, @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定キッチン削除.
     *
     * @param storeId       店舗ID
     * @param kitchenIdList キッチンIDリスト
     */
    @Modifying
    @Query(value = "DELETE FROM m_kitchen t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.kitchen_id IN :kitchenIdList ", nativeQuery = true)
    void deleteByStoreIdAndKitChenId(@Param("storeId") String storeId,
        @Param("kitchenIdList") List<Integer> kitchenIdList);
}
