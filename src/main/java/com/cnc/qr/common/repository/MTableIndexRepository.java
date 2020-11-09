package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MTableIndex;
import com.cnc.qr.core.pc.model.AreaInfoDto;
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
 * テーブル索引マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MTableIndexRepository extends JpaRepository<MTableIndex, Long> {

    /**
     * 指定店舗テーブルエリア情報取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 店舗テーブルエリア情報
     */
    List<MTableIndex> findByStoreIdAndDelFlagOrderByTableIndexIdAsc(String storeId,
        Integer delFlag);

    /**
     * 指定店舗のテーブルエリア情報取得.
     *
     * @param storeId 店舗ID
     * @return 店舗テーブルエリア情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.AreaInfoDto ( "
        + "t1.tableIndexId, "
        + "t1.areaName) "
        + "FROM MTableIndex t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.delFlag = 0 "
        + "ORDER BY t1.tableIndexId ASC ")
    List<AreaInfoDto> findAreaInfoByStoreId(@Param("storeId") String storeId);

    /**
     * 指定エリア情報取得.
     *
     * @param storeId      店舗ID
     * @param tableIndexId エリアID
     * @param delFlag      削除フラグ
     * @return エリア情報
     */
    MTableIndex findByStoreIdAndTableIndexIdAndDelFlag(String storeId, Integer tableIndexId,
        Integer delFlag);

    /**
     * 指定エリア情報ロック.
     *
     * @param storeId      店舗ID
     * @param tableIndexId エリアID
     * @return エリア情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MTableIndex t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableIndexId = :tableIndexId "
        + "AND t1.delFlag = 0")
    MTableIndex findByAreaIdForLock(@Param("storeId") String storeId,
        @Param("tableIndexId") Integer tableIndexId);

    /**
     * 指定エリアID削除.
     *
     * @param storeId          店舗ID
     * @param tableIndexIdList エリアIDリスト
     * @param updOperCd        更新者
     * @param updDateTime      更新日時
     */
    @Modifying
    @Query(value = "UPDATE MTableIndex t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.tableIndexId IN :tableIndexIdList ")
    void updateDelFlagByTableId(@Param("storeId") String storeId,
        @Param("tableIndexIdList") List<Integer> tableIndexIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

}
