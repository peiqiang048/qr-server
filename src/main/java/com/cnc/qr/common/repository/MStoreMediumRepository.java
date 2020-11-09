package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MStoreMedium;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 店舗媒体マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MStoreMediumRepository extends JpaRepository<MStoreMedium, Long> {

    /**
     * 指定店舗媒体情報取得.
     *
     * @param storeId             店舗ID
     * @param mediumType          店舗媒体种类
     * @param terminalDistinction 店舗媒体用途区分
     * @param delFlag             削除フラグ
     * @return 店舗媒体情報
     */
    List<MStoreMedium> findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
        String storeId, String mediumType, String terminalDistinction, Integer delFlag);

    /**
     * 店舗媒体情報取得.
     *
     * @param storeId 店舗ID
     * @return 店舗媒体情報
     */
    @Query(value = "SELECT " +
        "t1.medium_id AS mediaId, " +
        "t1.medium_type AS mediaType, " +
        "t1.terminal_distinction AS mediaUse, " +
        "t1.image_path AS mediaUrl " +
        "FROM m_store_medium t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.sort_order ASC", nativeQuery = true)
    List<Map<String, Object>> findMediaInfo(@Param("storeId") String storeId);

    /**
     * 指定店舗媒体情報を削除する.
     *
     * @param storeId 店舗ID
     * @param idList  媒体种类IDリスト
     */
    @Modifying
    @Query(value = "delete from m_store_medium "
        + "WHERE store_id = :storeId "
        + "AND medium_id in :idList ", nativeQuery = true)
    void deleteStoreMediums(@Param("storeId") String storeId,
        @Param("idList") List<Integer> idList);


}
