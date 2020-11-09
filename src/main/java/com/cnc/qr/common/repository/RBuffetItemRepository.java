package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RBuffetItem;
import com.cnc.qr.core.order.model.BuffetCourseItemDto;
import com.cnc.qr.core.order.model.ItemDto;
import com.cnc.qr.core.pc.model.ItemListDto;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 放題商品選択テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RBuffetItemRepository extends JpaRepository<RBuffetItem, Long> {

    /**
     * 放題商品件数取得.
     *
     * @param storeId  店舗ID
     * @param buffetId 放題商品ID
     * @param itemId   商品ID
     * @return 件数
     */
    @Query(value = "SELECT COUNT(t1) "
        + "FROM r_buffet_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.buffet_id = :buffetId "
        + "AND t1.item_id = :itemId "
        + "AND t1.del_flag = 0", nativeQuery = true)
    Integer getBuffetItemCount(@Param("storeId") String storeId,
        @Param("buffetId") Integer buffetId, @Param("itemId") Integer itemId);

    /**
     * コース商品取得.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.ItemDto("
        + "t2.itemName) "
        + "from RBuffetItem t1 "
        + "INNER JOIN MItem t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.itemId = t2.itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.buffetId = :itemId ")
    List<ItemDto> getItemList(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId);

    /**
     * コース商品取得.
     *
     * @param storeId  店舗ID
     * @param courseId コース商品ID
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.ItemListDto("
        + "t1.itemId, "
        + "t2.itemName, "
        + "t1.itemId as categoryId) "
        + "from RBuffetItem t1 "
        + "INNER JOIN MItem t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.itemId = t2.itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.buffetId = :courseId ")
    List<ItemListDto> getCourseItemList(@Param("storeId") String storeId,
        @Param("courseId") Integer courseId);

    /**
     * 飲み放題商品取得.
     *
     * @param storeId  店舗ID
     * @param buffetId 放題商品ID
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.ItemListDto("
        + "t1.itemId, "
        + "t2.itemName, "
        + "t1.itemId as categoryId) "
        + "from RBuffetItem t1 "
        + "INNER JOIN MItem t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.itemId = t2.itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.buffetId = :buffetId ")
    List<ItemListDto> getBuffetItemList(@Param("storeId") String storeId,
        @Param("buffetId") Integer buffetId);

    /**
     * 飲み放題商品IDリスト取得.
     *
     * @param storeId  店舗ID
     * @param buffetId 放題商品ID
     * @return 商品情報
     */
    @Query(value = "SELECT item_id "
        + "FROM r_buffet_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.buffet_id = :buffetId "
        + "AND t1.del_flag = 0", nativeQuery = true)
    List<Integer> getItemIdList(@Param("storeId") String storeId,
        @Param("buffetId") Integer buffetId);

    /**
     * 飲み放題商品情報削除.
     *
     * @param storeId    店舗ID
     * @param buffetId   放題商品ID
     * @param itemIdList 商品IDリスト
     */
    @Modifying
    @Query(value = "delete from r_buffet_item where store_id = :storeId "
        + "and buffet_id = :buffetId and del_flag = 0 "
        + "and item_id in :itemIdList", nativeQuery = true)
    void deleteItem(@Param("storeId") String storeId, @Param("buffetId") Integer buffetId,
        @Param("itemIdList") List<Integer> itemIdList);

    /**
     * 指定放題ID削除.
     *
     * @param storeId      店舗ID
     * @param buffetIdList 放題IDリスト
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "UPDATE r_buffet_item "
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
}
