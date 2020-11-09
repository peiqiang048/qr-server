package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MUnit;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
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
 * 商品単位マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MUnitRepository extends JpaRepository<MUnit, Long> {

    /**
     * 单位一覧情報取得.
     *
     * @param storeId  店舗ID
     * @param delFlag  削除フラグ
     * @param pageable ページ情報
     * @return 单位一覧
     */
    Page<MUnit> findByStoreIdAndDelFlag(String storeId, Integer delFlag, Pageable pageable);

    /**
     * 单位情報取得(名称用).
     *
     * @param storeId 店舗ID
     * @param unitId  单位ID
     * @param delFlag 削除フラグ
     * @return 单位情報
     */
    MUnit findByStoreIdAndUnitIdAndDelFlag(String storeId, Integer unitId, Integer delFlag);

    /**
     * 単位リスト取得.
     *
     * @param storeId 店舗ID
     * @return 単位リスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetUnitListOutputDto(" +
        "t1.unitId, " +
        "t1.unitName as unitName) " +
        "from MUnit t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 ")
    List<GetUnitListOutputDto> getUnitList(@Param("storeId") String storeId);

    /**
     * 指定单位IDのテーブル情報ロック.
     *
     * @param storeId 店舗ID
     * @param unitId  单位ID
     * @return 单位情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MUnit t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.unitId = :unitId "
        + "AND t1.delFlag = 0")
    MUnit findByUnitIdForLock(@Param("storeId") String storeId,
        @Param("unitId") Integer unitId);

    /**
     * 指定单位ID削除.
     *
     * @param storeId     店舗ID
     * @param unitIdList  单位IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MUnit t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.unitId IN :unitIdList ")
    void updateDelFlagByUnitId(@Param("storeId") String storeId,
        @Param("unitIdList") List<Integer> unitIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定单位ID登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_unit(" +
        "store_id," +
        "unit_id," +
        "unit_name," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":unitId," +
        "CAST(:unitName AS JSONB)," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void insertUnit(@Param("storeId") String storeId,
        @Param("unitId") Integer unitId,
        @Param("unitName") String unitName,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 指定单位ID更新処理.
     *
     * @param storeId     店舗ID
     * @param unitName    单位名
     * @param unitId      单位ID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update m_unit "
        + "set unit_name = CAST(:unitName AS JSONB), "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where store_id = :storeId "
        + "and unit_id = :unitId "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateUnit(@Param("storeId") String storeId,
        @Param("unitName") String unitName,
        @Param("unitId") Integer unitId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 单位一覧取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @param pageable  ページ情報
     * @return 单位一覧
     */
    @Query(value = "SELECT mi.unit_id as unitId, " +
        "mi.unit_name ->> :languages as unitName, " +
        "ROW_NUMBER() OVER(ORDER BY mi.unit_id ASC) AS num  " +
        "FROM m_unit mi " +
        "where mi.store_id = :storeId " +
        "and mi.del_flag = 0 " +
        "order by mi.unit_id asc", nativeQuery = true)
    Page<Map<String, Object>> findByStoreIdUnitInfo(@Param("storeId") String storeId,
        @Param("languages") String languages, Pageable pageable);
}
