package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RItem;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 関連商品テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RItemRepository extends JpaRepository<RItem, Long> {

    /**
     * 获取放题包含的コース的コースID.
     *
     * @param storeId        店舗ID
     * @param courseBuffetId 商品ID
     * @param code           削除フラグ
     * @return 商品情報
     */
    RItem findByStoreIdAndBuffetIdAndDelFlag(String storeId, Integer courseBuffetId,
        Integer code);

    /**
     * 附加商品ID更新処理.
     *
     * @param storeId     店舗ID
     * @param buffetId    放題商品ID
     * @param itemId      商品ID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update r_item "
        + "set item_id = :itemId, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and buffet_id = :buffetId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateBuffetItem(@Param("storeId") String storeId,
        @Param("buffetId") Integer buffetId,
        @Param("itemId") Integer itemId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定放題ID削除.
     *
     * @param storeId      店舗ID
     * @param buffetIdList 放題商品IDリスト
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "UPDATE r_item "
        + "SET del_flag = 1, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId "
        + "AND buffet_id IN :buffetIdList ", nativeQuery = true)
    void updateDelFlagByBuffetId(@Param("storeId") String storeId,
        @Param("buffetIdList") List<Integer> buffetIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 商品関連テーブル情報削除.
     *
     * @param storeId  店舗ID
     * @param buffetId 放題商品ID
     */
    void deleteByStoreIdAndBuffetId(String storeId, Integer buffetId);

}
