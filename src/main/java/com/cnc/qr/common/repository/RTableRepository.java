package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * テーブルリポジトリ.
 */
public interface RTableRepository extends JpaRepository<RTable, String> {

    /**
     * グループ受付取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 件数
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_table t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.receivables_id = :receivablesId " +
        "AND t1.real_receivables_id <> :receivablesId " +
        "AND t1.del_flag = 0", nativeQuery = true)
    Integer checkGroupOrder(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * グループ受付ID取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return グループ受付ID
     */
    @Query(value = "SELECT receivables_id " +
        "FROM r_table t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.real_receivables_id = :receivablesId " +
        "AND t1.receivables_id <> :receivablesId " +
        "AND t1.del_flag = 0", nativeQuery = true)
    List<String> getReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * クリアテーブル関連情報.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    void deleteByStoreIdAndRealReceivablesId(String storeId, String receivablesId);

    /**
     * グループ桌台数取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 件数
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_table t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.group_id = ( SELECT group_id " +
        "FROM r_table t2 " +
        "WHERE t2.store_id = :storeId " +
        "AND t2.receivables_id = :receivablesId " +
        "AND t2.del_flag = 0) " +
        "AND t1.del_flag = 0", nativeQuery = true)
    Integer getGroupTableNumber(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 指定受付ID削除.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    @Modifying
    @Query(value = "delete from r_table "
        + "where store_id = :storeId "
        + "and receivables_id = :receivablesId", nativeQuery = true)
    void deleteReceivables(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 指定グループID削除.
     *
     * @param storeId 店舗ID
     * @param groupId グループID
     */
    @Modifying
    @Query(value = "delete from r_table "
        + "where store_id = :storeId "
        + "and group_id = :groupId", nativeQuery = true)
    void deleteGroup(@Param("storeId") String storeId, @Param("groupId") Integer groupId);

    /**
     * 指定受付IDのテーブル情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param delFlag       削除フラグ
     * @return テーブル情報
     */
    RTable findByStoreIdAndReceivablesIdAndDelFlag(String storeId, String receivablesId,
        Integer delFlag);
}
