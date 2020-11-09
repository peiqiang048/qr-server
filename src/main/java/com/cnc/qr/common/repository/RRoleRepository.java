package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RRole;
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
 * ユーザ役割テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RRoleRepository extends JpaRepository<RRole, Long> {

    /**
     * 指定店舗の役割一覧情報取得.
     *
     * @param storeId           店舗ID
     * @param pageable          ページ情報
     * @param codeGroupStopFlag 停用标识
     * @param roleIds           角色IDリスト
     * @return 役割一覧
     */
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY t1.role_id ASC) AS num, "
        + "t1.role_id AS roleId, "
        + "t1.role_name AS roleName,"
        + "t2.code_name AS status  "
        + "FROM r_role t1 "
        + "LEFT JOIN m_code t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t2.code_group = :codeGroupStopFlag "
        + "AND t2.code = cast(t1.stop_flag as varchar(2)) "
        + "AND t2.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "AND t1.role_id NOT IN (:roleIds)  "
        + "ORDER BY t1.role_id ASC ", nativeQuery = true)
    Page<Map<String, Object>> findRoleByStoreId(@Param("storeId") String storeId,
        Pageable pageable, @Param("codeGroupStopFlag") String codeGroupStopFlag,
        @Param("roleIds") List<Integer> roleIds);

    /**
     * 指定角色IDの役割情報取得.
     *
     * @param storeId 店舗ID
     * @param roleId  角色ID
     * @param delFlag 削除フラグ
     * @return 役割情報
     */
    RRole findByStoreIdAndRoleIdAndDelFlag(String storeId, Integer roleId, Integer delFlag);

    /**
     * 指定役割IDの役割情報ロック.
     *
     * @param storeId 店舗ID
     * @param roleId  角色ID
     * @return 役割情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM RRole t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.roleId = :roleId "
        + "AND t1.delFlag = 0")
    RRole findByRoleIdForLock(@Param("storeId") String storeId, @Param("roleId") Integer roleId);

    /**
     * 指定役割ID削除.
     *
     * @param storeId     店舗ID
     * @param roleIdList  角色IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RRole t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.roleId IN :roleIdList ")
    void updateDelFlagByRoleId(@Param("storeId") String storeId,
        @Param("roleIdList") List<Integer> roleIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定役割ステータス更新.
     *
     * @param storeId     店舗ID
     * @param roleIdList  角色IDリスト
     * @param stopFlag    停用标识
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RRole t1 "
        + "SET t1.stopFlag = :stopFlag, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.roleId IN :roleIdList ")
    void updateStopFlagByRoleId(@Param("storeId") String storeId,
        @Param("roleIdList") List<Integer> roleIdList,
        @Param("stopFlag") Integer stopFlag,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定店舗IDの役割情報取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 役割情報
     */
    List<RRole> findByStoreIdAndDelFlag(String storeId, Integer delFlag);

}
