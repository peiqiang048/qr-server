package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MOption;
import com.cnc.qr.core.pc.model.GetOption;
import com.cnc.qr.core.pc.model.OptionCodeDto;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * オプションマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MOptionRepository extends JpaRepository<MOption, Long> {

    /**
     * オプション一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param optionTypeCd オプション種類コード
     * @param optionName   オプション名称
     * @param pageable     ページ情報
     * @return オプション一覧情報
     */
    @Query(value =
        "SELECT ROW_NUMBER() OVER(ORDER BY t1.sort_order ASC, t1.id, t2.sort_order ASC, t2.id ASC) AS num,"
            + "t1.option_type_cd AS optionTypeCd,"
            + "t1.option_code AS optionCode, "
            + "t1.option_name ->> :languages AS optionName, "
            + "t2.option_type_name ->> :languages AS optionTypeName "
            + "FROM m_option t1 "
            + "INNER JOIN m_option_type t2 "
            + "ON t2.store_id = t1.store_id "
            + "AND t2.option_type_cd = t1.option_type_cd "
            + "WHERE t1.store_id = :storeId "
            + "AND (t1.option_type_cd = :optionTypeCd OR :optionTypeCd = '') "
            + "AND ((t1.option_name ->> :languages) LIKE '%'||:optionName||'%' OR :optionName = '') "
            + "AND t1.del_flag = 0 "
            + "AND t2.del_flag = 0 "
            + "ORDER BY t1.sort_order ASC, t1.id, t2.sort_order ASC, t2.id ASC", nativeQuery = true)
    Page<Map<String, Object>> getOptionList(@Param("storeId") String storeId,
        @Param("languages") String languages,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("optionName") String optionName, Pageable pageable);

    /**
     * オプション情報取得.
     *
     * @param storeId      店舗ID
     * @param optionCode   オプションコード
     * @param optionTypeCd オプション種類コード
     * @return オプション一覧情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetOption( "
        + "t1.optionCode AS optionCode, "
        + "t1.optionName AS optionName, "
        + "t1.optionTypeCd AS optionTypeCd) "
        + "FROM MOption t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.optionTypeCd = :optionTypeCd "
        + "AND t1.optionCode = :optionCode "
        + "AND t1.delFlag = 0 "
        + "order by t1.sortOrder asc, t1.id")
    GetOption getOption(@Param("storeId") String storeId, @Param("optionCode") String optionCode,
        @Param("optionTypeCd") String optionTypeCd);

    /**
     * サブカテゴリー数量取得.
     *
     * @param storeId          店舗ID
     * @param optionTypeCdList オプション種類コードリスト
     * @return 数量
     */
    @Query(value = "SELECT count(*) "
        + "FROM m_option mo "
        + "where mo.store_id = :storeId "
        + "and mo.option_type_cd in :optionTypeCdList "
        + "and mo.del_flag = 0 ", nativeQuery = true)
    Integer getOptionCount(@Param("storeId") String storeId,
        @Param("optionTypeCdList") List<String> optionTypeCdList);

    /**
     * オプションマスタテーブル登録.
     */
    @Modifying
    @Query(value = ""
        + "INSERT INTO m_option("
        + "store_id,"
        + "option_type_cd,"
        + "option_code,"
        + "option_name,"
        + "sort_order,"
        + "del_flag,"
        + "ins_oper_cd,"
        + "ins_date_time,"
        + "upd_oper_cd,"
        + "upd_date_time,"
        + "version)"
        + "VALUES( "
        + ":storeId,"
        + ":optionTypeCd,"
        + ":optionCode,"
        + "CAST(:optionName AS JSONB),"
        + ":sortOrder,"
        + "0,"
        + ":operCd,"
        + ":dateTime,"
        + ":operCd,"
        + ":dateTime,"
        + "0)", nativeQuery = true)
    void insertOption(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("optionCode") String optionCode,
        @Param("optionName") String optionName,
        @Param("sortOrder") Integer sortOrder,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * オプション情報ロック.
     *
     * @param storeId    店舗ID
     * @param optionCode オプションコード
     * @return オプション情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MOption t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND (t1.optionCode = :optionCode OR :optionCode = '') "
        + "AND t1.delFlag = 0")
    MOption findByStoreIdAndOptionTypeCdAndOptionCodeForLock(@Param("storeId") String storeId,
        @Param("optionCode") String optionCode);

    /**
     * オプション更新処理.
     *
     * @param storeId      店舗ID
     * @param optionCode   オプションコード
     * @param optionName   オプション名
     * @param optionTypeCd オプション種類コード
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "update m_option "
        + "set option_name = CAST(:optionName AS JSONB), "
        + "option_type_cd = :optionTypeCd, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where store_id = :storeId "
        + "and option_code = :optionCode "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateOption(@Param("storeId") String storeId,
        @Param("optionCode") String optionCode,
        @Param("optionName") String optionName,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * オプション順番取得.
     *
     * @param storeId 店舗ID
     * @return オプション情報
     */
    @Query(value = "SELECT t1.sort_order "
        + "FROM m_option t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "order by t1.sort_order desc "
        + "limit 1 ", nativeQuery = true)
    Integer getMaxSortOrder(@Param("storeId") String storeId);

    /**
     * オプションコード採番取得.
     *
     * @param storeId 店舗ID
     * @return オプションコード
     */
    @Query(value =
        "SELECT TO_CHAR( CAST(  COALESCE (MAX(t1.option_code), '0') AS INTEGER) + 1, 'FM0000')  "
            + "FROM m_option t1 "
            + "WHERE t1.store_id = :storeId ", nativeQuery = true)
    String getSeqNo(@Param("storeId") String storeId);

    /**
     * オプション情報削除.
     *
     * @param storeId      店舗ID
     * @param optionTypeCd オプション種類コード
     */
    @Modifying
    @Query(value = "DELETE FROM m_option "
        + "WHERE store_id = :storeId "
        + "AND option_code = :optionCode "
        + "AND option_type_cd = :optionTypeCd ", nativeQuery = true)
    void deleteByoptionCodeList(@Param("storeId") String storeId,
        @Param("optionCode") String optionCode, @Param("optionTypeCd") String optionTypeCd);

    /**
     * オプション順番確定.
     *
     * @param storeId      店舗ID
     * @param optionTypeCd オプション種類コード
     * @param optionCode   オプションコード
     * @param sortOrder    表示順
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "UPDATE MOption t1 "
        + "SET t1.sortOrder = :sortOrder, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND (t1.optionTypeCd = :optionTypeCd OR :optionTypeCd = '') "
        + "AND t1.optionCode = :optionCode ")
    void updateOptionSort(@Param("storeId") String storeId,
        @Param("optionTypeCd") String optionTypeCd,
        @Param("optionCode") String optionCode,
        @Param("sortOrder") Integer sortOrder,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * オプション順番情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param optionTypeCd オプション種類コード
     * @return 順番情報
     */
    @Query(value = "SELECT "
        + "t1.option_type_cd AS optionTypeCd,"
        + "t1.option_code AS optionCode, "
        + "t1.option_name ->> :languages AS optionName, "
        + "t1.sort_order AS optionSortOrder "
        + "FROM m_option t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND (t1.option_type_cd = :optionTypeCd OR :optionTypeCd = '') "
        + "AND t1.del_flag = 0 "
        + "order by t1.sort_order asc, t1.id ", nativeQuery = true)
    List<Map<String, Object>> getOptionSortOrderList(@Param("storeId") String storeId,
        @Param("languages") String languages,
        @Param("optionTypeCd") String optionTypeCd);
}
