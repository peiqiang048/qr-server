package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RCategoryItem;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetItemOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * カテゴリ商品関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RCategoryItemRepository extends JpaRepository<RCategoryItem, Long> {

    /**
     * 商品カテゴリー情報取得.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @return カテゴリー情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto(" +
        "t1.categoryId, " +
        "t2.categoryName) " +
        "from RCategoryItem t1 " +
        "inner join MCategory t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.categoryId = t2.categoryId " +
        "AND t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId = :itemId ")
    List<GetCategoryIdListOutputDto> getCategoryIdList(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId);

    /**
     * 商品カテゴリー関連情報削除.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     */
    @Modifying
    @Query(value = "delete from r_category_item "
        + "where store_id = :storeId "
        + "and item_id = :itemId", nativeQuery = true)
    void deleteItemCategory(@Param("storeId") String storeId, @Param("itemId") Integer itemId);

    /**
     * 指定商品IDのカテゴリー関連情報削除.
     *
     * @param storeId    店舗ID
     * @param itemIdList 商品IDリスト
     */
    @Modifying
    @Query(value = "delete from r_category_item "
        + "where store_id = :storeId "
        + "and item_id in :itemIdList", nativeQuery = true)
    void deleteByItemIdList(@Param("storeId") String storeId,
        @Param("itemIdList") List<Integer> itemIdList);

    /**
     * カテゴリーの下に商品リスト情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return 商品情報リスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryItemOutputDto(" +
        "t2.itemId, " +
        "t2.itemName) " +
        "from RCategoryItem t1 " +
        "inner join MItem t2 " +
        "on t1.storeId = t2.storeId " +
        "and t1.itemId = t2.itemId " +
        "and t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.categoryId = :categoryId " +
        "order by t1.sortOrder asc")
    List<GetCategoryItemOutputDto> getItemList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);

    /**
     * 商品順番関連情報削除.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     */
    @Modifying
    @Query(value = "delete from r_category_item "
        + "where store_id = :storeId "
        + "and category_id = :categoryId", nativeQuery = true)
    void deleteItemSortOrder(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);

    /**
     * 飲み放題商品順番関連情報削除.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param itemIdList 商品IDリスト
     */
    @Modifying
    @Query(value = "delete from r_category_item "
        + "where store_id = :storeId "
        + "and category_id = :categoryId "
        + "AND item_id in :itemIdList", nativeQuery = true)
    void deleteBuffetItemSortOrder(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("itemIdList") List<Integer> itemIdList);

    /**
     * 商品順番情報取得.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @param code    削除フラグ
     * @return 商品情報リスト
     */
    List<RCategoryItem> findByStoreIdAndItemIdAndDelFlag(String storeId, Integer itemId,
        Integer code);

    /**
     * 商品順番編集.
     *
     * @param storeId     店舗ID
     * @param categoryId  カテゴリーID
     * @param sortOrder   順番
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RCategoryItem t1 "
        + "SET t1.sortOrder = t1.sortOrder - 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.categoryId = :categoryId "
        + "AND t1.sortOrder > :sortOrder ")
    void updateItemSortOrder(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 商品順番取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return 商品順番
     */
    @Query(value = "SELECT t1.sort_order " +
        "from r_category_item t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.category_id = :categoryId " +
        "AND t1.del_flag = 0 " +
        "order by t1.sort_order desc " +
        "limit 1 ", nativeQuery = true)
    Integer getMaxSortOrder(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);

    /**
     * 商品数量取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return 数量
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_category_item t1 " +
        "where t1.store_id = :storeId " +
        "and t1.category_id = :categoryId " +
        "and t1.del_flag = 0 ", nativeQuery = true)
    Integer getItemCount(@Param("storeId") String storeId, @Param("categoryId") Integer categoryId);

    /**
     * 商品順番情報取得.
     *
     * @param storeId    店舗ID
     * @param itemIdList 商品IDリスト
     * @return 商品順番情報
     */
    @Query(value = "SELECT t1 " +
        "from RCategoryItem t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId in :itemIdList ")
    List<RCategoryItem> getCategoryItemSort(@Param("storeId") String storeId,
        @Param("itemIdList") List<Integer> itemIdList);

    /**
     * 商品カテゴリー関連情報削除.
     *
     * @param storeId        店舗ID
     * @param itemId         商品ID
     * @param categoryIdList カテゴリーIDリスト
     */
    @Modifying
    @Query(value = "delete from r_category_item where store_id = :storeId "
        + "and item_id = :itemId and del_flag = 0 "
        + "and category_id in :categoryIdList", nativeQuery = true)
    void deleteCategoryItem(@Param("storeId") String storeId, @Param("itemId") Integer itemId,
        @Param("categoryIdList") List<Integer> categoryIdList);

    /**
     * 商品数量取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return 商品数量
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_category_item rci " +
        "where rci.store_id = :storeId " +
        "and rci.category_id in :categoryId " +
        "and rci.del_flag = 0 ", nativeQuery = true)
    Integer getItemCount(@Param("storeId") String storeId,
        @Param("categoryId") List<Integer> categoryId);

    /**
     * コース商品取得.
     *
     * @param storeId 店舗ID
     * @param idList  商品IDリスト
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.ItemListDto("
        + "t1.itemId, "
        + "t2.itemName, "
        + "t1.categoryId as categoryId) "
        + "from RCategoryItem t1 "
        + "INNER JOIN MItem t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.itemId = t2.itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t2.itemType in :idList ")
    List<ItemListDto> getItemList(@Param("storeId") String storeId,
        @Param("idList") List<String> idList);

    /**
     * カテゴリーの下に商品リスト情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param languages  言語
     * @param itemType   商品区分
     * @return 商品情報
     */
    @Query(value = "SELECT " +
        "t2.item_id as buffetId, " +
        "t2.item_name ->> :languages as buffetName," +
        "ROW_NUMBER() OVER(ORDER BY t1.sort_order ASC) AS num  " +
        "from r_category_item t1 " +
        "inner join m_item t2 " +
        "on t1.store_id = t2.store_id " +
        "and t1.item_id = t2.item_id " +
        "and t2.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 " +
        "AND t1.category_id = :categoryId " +
        "AND t2.item_type = :itemType " +
        "order by t1.sort_order asc", nativeQuery = true)
    List<Map<String, Object>> getItemListByItemType(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("itemType") String itemType);
}
