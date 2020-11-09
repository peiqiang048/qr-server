package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MCategory;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCategoryOutputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
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
 * カテゴリマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MCategoryRepository extends JpaRepository<MCategory, Long> {

    /**
     * 商品カテゴリー情報取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @param delFlag   削除フラグ
     * @return カテゴリー情報
     */
    List<MCategory> findByStoreIdAndGradationAndDelFlagOrderBySortOrderAsc(String storeId,
        Integer gradation, Integer delFlag);

    /**
     * カテゴリー情報取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @return カテゴリー情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryList(" +
        "t1.categoryId, " +
        "t1.categoryName as categoryName) " +
        "from MCategory t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.delFlag = 0 " +
        "order by t1.sortOrder asc, t1.id asc")
    List<GetCategoryList> getCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation);

    /**
     * 商品カテゴリーリスト取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @return 商品カテゴリーリスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetItemCategoryOutputDto(" +
        "t1.categoryId, " +
        "t1.categoryName) " +
        "from MCategory t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.delFlag = 0 " +
        "order by t1.sortOrder asc, t1.id asc")
    List<GetItemCategoryOutputDto> getItemCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation);

    /**
     * 指定親カテゴリーIDの子カテゴリーリスト情報取得.
     *
     * @param storeId          店舗ID
     * @param gradation        カテゴリレベル
     * @param parentCategoryId 親カテゴリーID
     * @return 子カテゴリーリスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetItemCategoryOutputDto(" +
        "t1.categoryId, " +
        "t1.categoryName) " +
        "from MCategory t1 " +
        "inner join  RParentCategory r1 " +
        "on t1.storeId = r1.storeId " +
        "and t1.categoryId = r1.childCategoryId " +
        "and r1.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.delFlag = 0 " +
        "AND r1.parentCategoryId = :parentCategoryId " +
        "order by r1.sortOrder asc")
    List<GetItemCategoryOutputDto> getItemCategorySortList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation, @Param("parentCategoryId") Integer parentCategoryId);

    /**
     * PCカテゴリー一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param categoryName カテゴリー名
     * @param pageable     ページ情報
     * @return カテゴリーリスト
     */
    @Query(value = "SELECT mc.category_id as categoryId, "
        + "mc.category_name ->> :languages as categoryName,"
        + "ROW_NUMBER() OVER(ORDER BY mc.sort_order ASC) AS num "
        + "FROM m_category mc "
        + "where mc.store_id = :storeId "
        + "and mc.gradation = 1 "
        + "and mc.del_flag = 0 "
        + "and ((mc.category_name ->> :languages) LIKE  '%'||:categoryName||'%' OR :categoryName = '')"
        + "and mc.category_id > 0 "
        + "order by mc.sort_order asc", nativeQuery = true)
    Page<Map<String, Object>> getParentCategoryData(@Param("storeId") String storeId,
        @Param("languages") String languages,
        @Param("categoryName") String categoryName, Pageable pageable);

    /**
     * 指摘カテゴリーIDのカテゴリー情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @return カテゴリー情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryOutputDto(" +
        "t1.categoryName, " +
        "t1.gradation) " +
        "from MCategory t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.categoryId = :categoryId " +
        "AND t1.delFlag = 0 ")
    GetCategoryOutputDto getCategoryInfo(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId);

    /**
     * 親カテゴリーリスト取得.
     *
     * @param storeId 店舗ID
     * @return 親カテゴリーリスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCategoryList(" +
        "t1.categoryId, " +
        "t1.categoryName) " +
        "from MCategory t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.gradation = 1 " +
        "AND t1.delFlag = 0 " +
        "order by t1.sortOrder asc")
    List<GetCategoryList> getParentCategoryList(@Param("storeId") String storeId);

    /**
     * カテゴリー情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param code       削除フラグ
     * @return カテゴリー情報
     */
    MCategory findByStoreIdAndCategoryIdAndDelFlag(String storeId, Integer categoryId,
        Integer code);

    /**
     * 親カテゴリー順番取得.
     *
     * @param storeId 店舗ID
     * @return 親カテゴリー順番
     */
    @Query(value = "SELECT t1.sort_order " +
        "from m_category t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.gradation = 1 " +
        "AND t1.del_flag = 0 " +
        "order by t1.sort_order desc " +
        "limit 1 ", nativeQuery = true)
    Integer getMaxSortOrder(@Param("storeId") String storeId);

    /**
     * カテゴリーデータ登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_category(" +
        "store_id," +
        "category_id," +
        "category_name," +
        "sort_order," +
        "gradation," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":categoryId," +
        "CAST(:categoryName AS JSONB)," +
        ":sortOrder," +
        ":gradation," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void insertCategory(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId,
        @Param("categoryName") String categoryName,
        @Param("sortOrder") Integer sortOrder,
        @Param("gradation") Integer gradation,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 商品サマリ更新処理.
     *
     * @param storeId      店舗ID
     * @param categoryId   カテゴリーID
     * @param categoryName カテゴリー名
     * @param sortOrder    順番
     * @param gradation    カテゴリレベル
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "update m_category "
        + "set category_name = CAST(:categoryName AS JSONB), "
        + "sort_order = :sortOrder, "
        + "gradation = :gradation, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and category_id = :categoryId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateCategory(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId,
        @Param("categoryName") String categoryName,
        @Param("sortOrder") Integer sortOrder,
        @Param("gradation") Integer gradation,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 商品サマリ更新処理.
     *
     * @param storeId     店舗ID
     * @param sortOrder   順番
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update m_category "
        + "set sort_order = sort_order - 1, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and gradation = 1 "
        + "and sort_order > :sortOrder "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateSort(@Param("storeId") String storeId,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * カテゴリーリスト情報取得.
     *
     * @param storeId        店舗ID
     * @param categoryIdList カテゴリーIDリスト
     * @return カテゴリーリスト情報
     */
    @Query(value = "SELECT t1 " +
        "from MCategory t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.categoryId in :categoryIdList " +
        "AND t1.delFlag = 0 ")
    List<MCategory> getMstCategoryList(@Param("storeId") String storeId,
        @Param("categoryIdList") List<Integer> categoryIdList);

    /**
     * 指定カテゴリーID削除.
     *
     * @param storeId        店舗ID
     * @param categoryIdList カテゴリーIDリスト
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "UPDATE MCategory t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.categoryId IN :categoryIdList ")
    void updateDelFlagByCategoryId(@Param("storeId") String storeId,
        @Param("categoryIdList") List<Integer> categoryIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 親カテゴリー順番編集.
     *
     * @param storeId     店舗ID
     * @param categoryId  カテゴリーID
     * @param sortOrder   順番
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MCategory t1 "
        + "SET t1.sortOrder = :sortOrder, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.categoryId = :categoryId ")
    void updateCategorySort(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * カテゴリー情報取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @param languages 言語
     * @param idList    カテゴリーIDリスト
     * @param status    ステータス
     * @return カテゴリーリスト情報
     */
    @Query(value = "SELECT t1.category_id as itemCategoryId," +
        "t1.category_name ->> :languages as itemCategoryName, " +
        "t1.sort_order " +
        "from m_category t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.del_flag = 0 " +
        "AND t1.category_id in (" +
        "select distinct t2.category_id " +
        "from r_category_item t2, m_item t3 " +
        "where t2.store_id = t3.store_id " +
        "and t2.item_id = t3.item_id " +
        "and t2.store_id = :storeId " +
        "and t3.del_flag = 0 " +
        "and t3.item_type in :idList " +
        "and t3.status = :status " +
        "and t2.del_flag = 0 ) " +
        "order by t1.sort_order asc, t1.id asc", nativeQuery = true)
    List<Map<String, Object>> getHasItemCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation, @Param("languages") String languages,
        @Param("idList") List<String> idList, @Param("status") String status);

    /**
     * 放題カテゴリー情報取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @param buffetId  放題商品IDリスト
     * @param idList    商品区分リスト
     * @return 放題カテゴリー情報
     */
    @Query(value = "SELECT t7.itemCategoryId,"
        + "CASE WHEN MAX(buffetFlag) = '1' AND MIN(buffetFlag) = '0' THEN '2' ELSE MAX(buffetFlag) END AS buffetFlag "
        + "FROM (SELECT t1.category_id as itemCategoryId,"
        + "'1' AS buffetFlag "
        + "FROM m_category t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.gradation = :gradation "
        + "AND t1.del_flag = 0 "
        + "AND EXISTS ("
        + "SELECT true "
        + "FROM r_category_item t2, r_buffet_item t3 "
        + "WHERE t1.store_id = t2.store_id "
        + "AND t1.category_id = t2.category_id "
        + "AND t3.store_id = t2.store_id "
        + "AND t3.item_id = t2.item_id "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "AND t3.buffet_id IN :buffetId) "
        + "UNION ALL "
        + "SELECT t4.category_id as itemCategoryId,"
        + "'0' AS buffetFlag "
        + "FROM m_category t4 "
        + "WHERE t4.store_id = :storeId "
        + "AND t4.gradation = :gradation "
        + "AND t4.del_flag = 0 "
        + "AND EXISTS ("
        + "SELECT true "
        + "FROM r_category_item t5, m_item t8 "
        + "WHERE t4.store_id = t5.store_id "
        + "AND t4.category_id = t5.category_id "
        + "AND t5.del_flag = 0 "
        + "AND t5.store_id = t8.store_id "
        + "AND t5.item_id = t8.item_id "
        + "AND t8.del_flag = 0 "
        + "AND t8.item_type IN :idList "
        + "AND NOT EXISTS ("
        + "   SELECT true"
        + "   FROM r_buffet_item t6"
        + "   WHERE t5.store_id = t6.store_id"
        + "   AND t5.item_id = t6.item_id"
        + "   AND t6.buffet_id IN :buffetId"
        + "   AND t6.del_flag = 0 "
        + "))) t7 GROUP BY t7.itemCategoryId ", nativeQuery = true)
    List<Map<String, Object>> getBuffetItemCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation, @Param("buffetId") List<Integer> buffetId,
        @Param("idList") List<String> idList);

    /**
     * カテゴリーリスト取得.
     *
     * @param storeId   店舗ID
     * @param gradation カテゴリレベル
     * @param languages 言語
     * @param idList    商品区分リスト
     * @return カテゴリーリスト
     */
    @Query(value = "SELECT t1.category_id as categoryId," +
        "t1.category_name ->> :languages as categoryName " +
        "from m_category t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.del_flag = 0 " +
        "AND t1.category_id in (" +
        "select t2.category_id " +
        "from r_category_item t2, m_item t3 " +
        "where t2.store_id = t3.store_id " +
        "and t2.item_id = t3.item_id " +
        "and t2.store_id = :storeId " +
        "and t3.del_flag = 0 " +
        "and t3.item_type in :idList " +
        "and t2.del_flag = 0 ) " +
        "order by t1.sort_order asc, t1.id asc", nativeQuery = true)
    List<Map<String, Object>> getItemCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation, @Param("languages") String languages,
        @Param("idList") List<String> idList);

    /**
     * 出前カテゴリー情報取得.
     *
     * @param storeId          店舗ID
     * @param gradation        カテゴリレベル
     * @param languages        言語
     * @param deliveryTypeFlag 出前仕方フラグリスト
     * @param deliveryFlag     出前フラグ
     * @param idList           商品区分リスト
     * @return 出前カテゴリー情報
     */
    @Query(value = "SELECT t1.category_id as itemCategoryId," +
        "t1.category_name ->> :languages as itemCategoryName, " +
        "t1.sort_order " +
        "from m_category t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.gradation = :gradation " +
        "AND t1.del_flag = 0 " +
        "AND t1.category_id in (" +
        "select t2.category_id " +
        "from r_category_item t2, m_item t3 " +
        "where t2.store_id = t3.store_id " +
        "and t2.item_id = t3.item_id " +
        "and t2.store_id = :storeId " +
        "and t3.del_flag = 0 " +
        "and t3.item_type in :idList " +
        "and t3.delivery_flag = :deliveryFlag " +
        "and t3.delivery_type_flag in :deliveryTypeFlag " +
        "and t2.del_flag = 0 ) " +
        "order by t1.sort_order asc, t1.id asc", nativeQuery = true)
    List<Map<String, Object>> getHasDeliveryItemCategoryList(@Param("storeId") String storeId,
        @Param("gradation") Integer gradation, @Param("languages") String languages,
        @Param("deliveryTypeFlag") List<String> deliveryTypeFlag,
        @Param("deliveryFlag") String deliveryFlag,
        @Param("idList") List<String> idList);
}
