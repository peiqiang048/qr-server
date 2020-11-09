package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MOptionType;
import com.cnc.qr.core.pc.model.GetOptionTypeList;
import com.cnc.qr.core.pc.model.GetOptionTypeOutputDto;
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
 * オプション種類マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MOptionTypeRepository extends JpaRepository<MOptionType, Long> {

    /**
     * 商品オプション類型情報取得.
     *
     * @param storeId  店舗ID
     * @param itemId   商品ID
     * @param language 言語
     * @return 商品オプション類型情報
     */
    @Query(value =
        "select m_option_type.option_type_cd as optionTypeCd, "
            + "m_option_type.option_type_name ->> :language as optionTypeName, "
            + "m_option_type.sort_order as optionTypeSortOrder, m_option_type.classification, "
            + "m_option.option_code as optionCode, m_option.option_name ->> :language as optionName, "
            + "m_option.sort_order as optionSortOrder, r_item_option.diff_price as diffPrice "
            + "from r_item_option,m_option,m_option_type "
            + "where r_item_option.store_id = m_option_type.store_id "
            + "and r_item_option.option_type_code = m_option_type.option_type_cd "
            + "and r_item_option.del_flag = 0 "
            + "and m_option_type.del_flag = 0 "
            + "and r_item_option.store_id = m_option.store_id "
            + "and r_item_option.option_type_code = m_option.option_type_cd "
            + "and r_item_option.option_code = m_option.option_code "
            + "and m_option.del_flag = 0 "
            + "and r_item_option.store_id = :storeId "
            + "and r_item_option.item_id = :itemId "
            + "order by m_option_type.sort_order, m_option.sort_order ", nativeQuery = true)
    List<Map<String, Object>> findOptionTypeByItemId(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId, @Param("language") String language);

    /**
     * 商品オプション類型情報取得.
     *
     * @param storeId  店舗ID
     * @param language 言語
     * @return 商品オプション類型情報
     */
    @Query(value =
        "select m_option_type.option_type_cd as optionTypeCd, "
            + "m_option_type.option_type_name ->> :language as optionTypeName, "
            + "m_option.option_code as optionCode, m_option.option_name ->> :language as optionName "
            + "from m_option,m_option_type "
            + "where m_option_type.store_id = m_option.store_id "
            + "and m_option_type.option_type_cd = m_option.option_type_cd "
            + "and m_option_type.del_flag = 0 "
            + "and m_option.del_flag = 0 "
            + "and m_option_type.store_id = :storeId "
            + "order by m_option_type.sort_order, m_option.sort_order ", nativeQuery = true)
    List<Map<String, Object>> getOptionType(@Param("storeId") String storeId,
        @Param("language") String language);

    /**
     * 商品オプション種類情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 商品オプション類型情報
     */
    @Query(value =
        "select option_type_cd as optionTypeCd, "
            + "option_type_name ->> :languages as optionTypeName "
            + "from m_option_type "
            + "where store_id = :storeId "
            + "and del_flag = 0 "
            + "order by sort_order asc, id asc  ", nativeQuery = true)
    List<Map<String, Object>> getOptionTypeList(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * オプション種類情報取得.
     *
     * @param storeId   店舗ID
     * @param groupCode オプション種類区分
     * @param languages 言語
     * @return オプション類型情報
     */
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY t1.sort_order ASC) AS num,"
        + "t1.option_type_cd AS optionTypeCd,"
        + "t1.option_type_name ->> :languages as optionTypeName,"
        + "t2.code_name AS classification "
        + "from m_option_type t1 "
        + "INNER JOIN m_code t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.classification = t2.code "
        + "AND t2.code_group = :groupCode "
        + "AND t2.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.sort_order ", nativeQuery = true)
    List<Map<String, Object>> getOptionTypeLists(@Param("storeId") String storeId,
        @Param("groupCode") String groupCode, @Param("languages") String languages);

    /**
     * オプション種類明細情報取得.
     *
     * @param storeId      店舗ID
     * @param optionTypeCd オプション種類コード
     * @return オプション種類明細情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetOptionTypeOutputDto(" +
        "t1.optionTypeName," +
        "t1.classification) " +
        "from MOptionType t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.optionTypeCd = :optionTypeCd " +
        "AND t1.delFlag = 0 ")
    GetOptionTypeOutputDto getOptionTypeInfo(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd);

    /**
     * 指定オプション種類IDのオプション種類情報ロック.
     *
     * @param storeId      店舗ID
     * @param optionTypeCd オプション種類コード
     * @return オプション種類情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MOptionType t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.optionTypeCd = :optionTypeCd "
        + "AND t1.delFlag = 0")
    MOptionType findByOptionTypeCdForLock(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd);

    /**
     * 最大オプション種類コード取得.
     *
     * @param storeId 店舗ID
     * @return オプション種類コード
     */
    @Query(value = "SELECT t1.option_type_cd "
        + "FROM m_option_type t1 "
        + "WHERE t1.store_id = :storeId "
        + "order by cast(t1.option_type_cd as integer ) desc "
        + "limit 1 ", nativeQuery = true)
    Integer getMaxOptionTypeCode(@Param("storeId") String storeId);

    /**
     * 最大表示順取得.
     *
     * @param storeId 店舗ID
     * @return 表示順
     */
    @Query(value = "SELECT t1.sort_order "
        + "FROM m_option_type t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "order by t1.sort_order desc "
        + "limit 1 ", nativeQuery = true)
    Integer getMaxOptionTypeSort(@Param("storeId") String storeId);

    /**
     * オプション種類更新処理.
     *
     * @param storeId        店舗ID
     * @param optionTypeCd   オプション種類コード
     * @param optionTypeName オプション種類名
     * @param classification 区分
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "update m_option_type "
        + "set option_type_name = CAST(:optionTypeName AS JSONB), "
        + "classification = :classification, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and option_type_cd = :optionTypeCd "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateOptionType(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("optionTypeName") String optionTypeName,
        @Param("classification") String classification,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * オプション種類マスタテーブル登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_option_type(" +
        "store_id," +
        "option_type_cd," +
        "option_type_name," +
        "sort_order," +
        "classification," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":optionTypeCd," +
        "CAST(:optionTypeName AS JSONB)," +
        ":sortOrder," +
        ":classification," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void registOptionType(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("optionTypeName") String optionTypeName,
        @Param("sortOrder") Integer sortOrder,
        @Param("classification") String classification,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 指定オプション種類削除.
     *
     * @param storeId          店舗ID
     * @param optionTypeCdList オプション種類コードリスト
     * @param updOperCd        更新者
     * @param updDateTime      更新日時
     */
    @Modifying
    @Query(value = "UPDATE MOptionType t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.optionTypeCd IN :optionTypeCdList ")
    void updateDelFlagByOptionTypeCd(@Param("storeId") String storeId,
        @Param("optionTypeCdList") List<String> optionTypeCdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * オプション種類順番編集.
     *
     * @param storeId      店舗ID
     * @param optionTypeCd オプション種類コード
     * @param sortOrder    表示順
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "UPDATE MOptionType t1 "
        + "SET t1.sortOrder = :sortOrder, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.optionTypeCd = :optionTypeCd ")
    void updateOptionTypeSort(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
