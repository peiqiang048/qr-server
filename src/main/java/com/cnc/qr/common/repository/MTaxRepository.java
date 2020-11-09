package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MOption;
import com.cnc.qr.common.entity.MTax;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import io.swagger.models.auth.In;
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
 * 税設定マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MTaxRepository extends JpaRepository<MTax, Long> {

    /**
     * 税情報取得.
     *
     * @param storeId   店舗ID
     * @param applyDate 適用開始日時
     * @return 税情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetTaxListOutputDto(" +
        "t1.taxId, " +
        "t1.taxName) " +
        "from MTax t1 " +
        "INNER JOIN MStore t2 " +
        "ON t1.businessId = t2.businessId " +
        "AND t2.delFlag = 0 " +
        "WHERE t2.storeId = :storeId " +
        "AND t1.applyDate <= :applyDate " +
        "AND t1.delFlag = 0 ")
    List<GetTaxListOutputDto> getTaxList(@Param("storeId") String storeId,
        @Param("applyDate") ZonedDateTime applyDate);

    /**
     * 税設定一覧情報取得.
     *
     * @param businessId ビジネスID
     * @return 税設定一覧情報
     */
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY t1.tax_id ASC) AS num,"
        + "t1.tax_id AS taxId, "
        + "t1.tax_name AS taxName, "
        + "t1.tax_relief_apply_type AS taxReliefApplyTypeCode, "
        + "TO_CHAR(t1.apply_date, 'YYYY-MM-DD HH24:MI:SS') AS applyDate  "
        + "FROM m_tax t1 "
        + "WHERE t1.business_id = :businessId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.tax_id ASC", nativeQuery = true)
    List<Map<String, Object>> findTaxInfoList(@Param("businessId") String businessId);

    /**
     * 税設定情報取得.
     *
     * @param businessId ビジネスID
     * @param taxId      税ID
     * @return 税設定情報
     */
    @Query(value = "SELECT " +
        "t1.tax_name AS taxName, " +
        "t1.tax_code AS taxCode, " +
        "t1.tax_round_type AS taxRoundType, " +
        "t1.tax_relief_apply_type AS taxReliefApplyType, " +
        "t1.tax_rate_normal AS taxRateNormal, " +
        "t1.tax_rate_relief AS taxRateRelief, " +
        "TO_CHAR(t1.apply_date, 'YYYY-MM-DD HH24:MI:SS') AS applyDate  " +
        "FROM m_tax t1 " +
        "WHERE t1.business_id = :businessId " +
        "AND t1.tax_id = :taxId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findTaxInfo(@Param("businessId") String businessId,
        @Param("taxId") Integer taxId);

    /**
     * 税設定順番取得.
     *
     * @param businessId ビジネスID
     * @return 税設定順番情報
     */
    @Query(value = "SELECT t1.sort_order "
        + "FROM m_tax t1 "
        + "WHERE t1.business_id = :businessId "
        + "AND t1.del_flag = 0 "
        + "order by t1.sort_order desc "
        + "limit 1 ", nativeQuery = true)
    Integer getMaxSortOrder(@Param("businessId") String businessId);

    /**
     * 税ID採番取得.
     *
     * @param businessId ビジネスID
     * @return 採番結果
     */
    @Query(value = "SELECT MAX(t1.tax_id) + 1 "
        + "FROM m_tax t1 "
        + "WHERE t1.business_id = :businessId ", nativeQuery = true)
    Integer getSeqNo(@Param("businessId") String businessId);

    /**
     * 税設定マスタテーブル登録.
     */
    @Modifying
    @Query(value = ""
        + "INSERT INTO m_option("
        + "business_id,"
        + "tax_id,"
        + "tax_name,"
        + "tax_code,"
        + "tax_round_type,"
        + "tax_relief_apply_type,"
        + "tax_rate_normal,"
        + "tax_rate_relief,"
        + "apply_date,"
        + "sort_order,"
        + "del_flag,"
        + "ins_oper_cd,"
        + "ins_date_time,"
        + "upd_oper_cd,"
        + "upd_date_time,"
        + "version)"
        + "VALUES( "
        + ":businessId,"
        + ":taxId,"
        + "CAST(:taxName AS JSONB),"
        + ":taxCode,"
        + ":taxRoundType,"
        + ":taxReliefApplyType,"
        + ":taxRateNormal,"
        + ":taxRateRelief,"
        + ":applyDate,"
        + ":sortOrder,"
        + "0,"
        + ":operCd,"
        + ":dateTime,"
        + ":operCd,"
        + ":dateTime,"
        + "0)", nativeQuery = true)
    void insertTax(@Param("businessId") String businessId,
        @Param("taxId") String taxId,
        @Param("taxName") String taxName,
        @Param("taxCode") String taxCode,
        @Param("taxRoundType") String taxRoundType,
        @Param("taxReliefApplyType") String taxReliefApplyType,
        @Param("taxRateNormal") Integer taxRateNormal,
        @Param("taxRateRelief") Integer taxRateRelief,
        @Param("applyDate") String applyDate,
        @Param("sortOrder") Integer sortOrder,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 税設定情報ロック.
     *
     * @param businessId ビジネスID
     * @param taxId      税ID
     * @return 税設定情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MTax t1 "
        + "WHERE t1.businessId = :businessId "
        + "AND t1.taxId = :taxId "
        + "AND t1.delFlag = 0")
    MTax findByBusinessIdAndTaxIdForLock(@Param("businessId") String businessId,
        @Param("taxId") Integer taxId);

    /**
     * 税設定更新処理.
     *
     * @param businessId         店舗ID
     * @param taxId              税ID
     * @param taxName            税名
     * @param taxCode            税区分
     * @param taxRoundType       税端数処理区分
     * @param taxReliefApplyType 軽減税率適用区分
     * @param taxRateNormal      標準税率
     * @param taxRateRelief      軽減税率
     * @param applyDate          適用日時
     * @param updOperCd          更新者
     * @param updDateTime        更新日時
     */
    @Modifying
    @Query(value = "update m_tax "
        + "set tax_name = :taxName, "
        + "tax_code = :taxCode, "
        + "tax_round_type = :taxRoundType, "
        + "tax_relief_apply_type = :taxReliefApplyType, "
        + "tax_rate_normal = :taxRateNormal, "
        + "tax_rate_relief = :taxRateRelief, "
        + "apply_date = :applyDate, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where business_id = :businessId "
        + "and tax_id = :taxId "
        + "and del_flag = 0 ", nativeQuery = true)
    void updateTax(@Param("businessId") String businessId,
        @Param("taxId") Integer taxId,
        @Param("taxName") String taxName,
        @Param("taxCode") String taxCode,
        @Param("taxRoundType") String taxRoundType,
        @Param("taxReliefApplyType") String taxReliefApplyType,
        @Param("taxRateNormal") Integer taxRateNormal,
        @Param("taxRateRelief") Integer taxRateRelief,
        @Param("applyDate") String applyDate,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定税ID削除.
     *
     * @param businessId  店舗ID
     * @param taxIdList   税IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MTax t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.businessId = :businessId "
        + "AND t1.taxId IN :taxIdList ")
    void updateDelFlagByTaxId(@Param("businessId") String businessId,
        @Param("taxIdList") List<Integer> taxIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);
}
