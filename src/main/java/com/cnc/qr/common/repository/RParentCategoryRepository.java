package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RParentCategory;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * カテゴリ関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RParentCategoryRepository extends JpaRepository<RParentCategory, Long> {

    /**
     * PCカテゴリー一覧情報取得.
     *
     * @param storeId          店舗ID
     * @param parentCategoryId 親カテゴリーID
     * @param languages        言語
     * @param categoryName     カテゴリー名
     * @param pageable         ページ情報
     * @return カテゴリー一覧
     */
    @Query(value = "SELECT mc.category_id as categoryId, "
        + "mc.category_name ->> :languages as categoryName, "
        + "ROW_NUMBER() OVER(ORDER BY rpc.sort_order ASC) AS num  "
        + "FROM r_parent_category rpc, m_category mc "
        + "where rpc.store_id = mc.store_id "
        + "and rpc.child_category_id = mc.category_id "
        + "and rpc.store_id = :storeId "
        + "and rpc.parent_category_id = :parentCategoryId "
        + "and rpc.del_flag = 0 "
        + "and mc.del_flag = 0 "
        + "and ((mc.category_name ->> :languages) LIKE  '%'||:categoryName||'%' OR :categoryName = '') "
        + "and mc.category_id > 0 "
        + "order by rpc.sort_order asc", nativeQuery = true)
    Page<Map<String, Object>> getCategoryData(@Param("storeId") String storeId,
        @Param("parentCategoryId") Integer parentCategoryId, @Param("languages") String languages,
        @Param("categoryName") String categoryName, Pageable pageable);

    /**
     * 親カテゴリーID取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return 親カテゴリーID情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto(" +
        "t1.parentCategoryId as categoryId, " +
        "t2.categoryName) " +
        "from RParentCategory t1 " +
        "inner join MCategory t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.parentCategoryId = t2.categoryId " +
        "AND t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.childCategoryId = :categoryId " +
        "AND t1.delFlag = 0 ")
    List<GetCategoryIdListOutputDto> getParentCategoryIdList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);

    /**
     * サブカテゴリー数量取得.
     *
     * @param storeId    店舗ID
     * @param categoryId 親カテゴリーID
     * @return 件数
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_parent_category rpc " +
        "where rpc.store_id = :storeId " +
        "and rpc.parent_category_id = :categoryId " +
        "and rpc.del_flag = 0 ", nativeQuery = true)
    Integer getChild(@Param("storeId") String storeId, @Param("categoryId") Integer categoryId);

    /**
     * サブカテゴリー順番取得.
     *
     * @param storeId    店舗ID
     * @param categoryId 親カテゴリーID
     * @return 順番
     */
    @Query(value = "SELECT rpc.sort_order " +
        "from r_parent_category rpc " +
        "WHERE rpc.store_id = :storeId " +
        "AND rpc.parent_category_id = :categoryId " +
        "AND rpc.del_flag = 0 " +
        "order by rpc.sort_order desc " +
        "limit 1 ", nativeQuery = true)
    Integer getMaxSort(@Param("storeId") String storeId, @Param("categoryId") Integer categoryId);

    /**
     * 同一父分类下子分类id与顺序取得.
     *
     * @param storeId    店舗ID
     * @param categoryId 親カテゴリーID
     * @param code       削除フラグ
     * @return カテゴリー情報
     */
    List<RParentCategory> findByStoreIdAndChildCategoryIdAndDelFlag(String storeId,
        Integer categoryId, Integer code);

    /**
     * サブカテゴリー順番変更.
     *
     * @param storeId     店舗ID
     * @param categoryId  親カテゴリーID
     * @param sortOrder   表示順
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update r_parent_category "
        + "set sort_order = sort_order - 1, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and parent_category_id = :categoryId "
        + "and sort_order = > :sortOrder "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateCategorySortOrder(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * カテゴリー関連情報削除.
     *
     * @param storeId    店舗ID
     * @param categoryId 子カテゴリID
     */
    @Modifying
    @Query(value = "delete from r_parent_category where store_id = :storeId "
        + "and child_category_id = :categoryId and del_flag = 0", nativeQuery = true)
    void deleteCategory(@Param("storeId") String storeId, @Param("categoryId") Integer categoryId);

    /**
     * カテゴリー関連情報削除.
     *
     * @param storeId      店舗ID
     * @param categoryId   子カテゴリID
     * @param parentIdList 親カテゴリーIDリスト
     */
    @Modifying
    @Query(value = "delete from r_parent_category where store_id = :storeId "
        + "and child_category_id = :categoryId and del_flag = 0 "
        + "and parent_category_id in :parentIdList", nativeQuery = true)
    void deleteCategory(@Param("storeId") String storeId, @Param("categoryId") Integer categoryId,
        @Param("parentIdList") List<Integer> parentIdList);

    /**
     * サブカテゴリー数量取得.
     *
     * @param storeId    店舗ID
     * @param categoryId 親カテゴリーID
     * @return カテゴリー情報
     */
    @Query(value = "SELECT count(*) " +
        "FROM r_parent_category rpc " +
        "where rpc.store_id = :storeId " +
        "and rpc.parent_category_id in :categoryId " +
        "and rpc.del_flag = 0 ", nativeQuery = true)
    Integer getChildCount(@Param("storeId") String storeId,
        @Param("categoryId") List<Integer> categoryId);

    /**
     * 指定サブカテゴリーID削除.
     *
     * @param storeId        店舗ID
     * @param categoryIdList 子カテゴリIDリスト
     */
    @Modifying
    @Query(value = "delete from r_parent_category "
        + "where store_id = :storeId "
        + "and child_category_id in :categoryIdList", nativeQuery = true)
    void deleteByChildIdList(@Param("storeId") String storeId,
        @Param("categoryIdList") List<Integer> categoryIdList);

    /**
     * 指定親カテゴリーID削除.
     *
     * @param storeId    店舗ID
     * @param categoryId 親カテゴリーID
     */
    @Modifying
    @Query(value = "delete from r_parent_category "
        + "where store_id = :storeId "
        + "and parent_category_id = :categoryId", nativeQuery = true)
    void deleteByParentId(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);
}
