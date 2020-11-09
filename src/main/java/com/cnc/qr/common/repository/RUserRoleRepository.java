package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RUserRole;
import com.cnc.qr.core.pc.model.RoleInfoDto;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ユーザ関連役割テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RUserRoleRepository extends JpaRepository<RUserRole, Long> {

    /**
     * 指定ユーザーIDの役割情報取得.
     *
     * @param storeId 店舗ID
     * @param userId  ユーザーID
     * @return 役割情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.RoleInfoDto ( "
        + "t1.roleId, "
        + "t2.roleName) "
        + "FROM RUserRole t1 "
        + "INNER JOIN RRole t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.roleId = t2.roleId "
        + "WHERE t1.storeId = :storeId "
        + "AND (t1.userId = :userId ) "
        + "AND t1.delFlag = 0 ")
    List<RoleInfoDto> findUserRoleByUserId(@Param("storeId") String storeId,
        @Param("userId") Integer userId);


    /**
     * 指定ユーザーIDの役割情報削除.
     *
     * @param storeId 店舗ID
     * @param userId  ユーザーID
     */
    @Modifying
    @Query(value = ""
        + "DELETE FROM RUserRole "
        + "WHERE storeId = :storeId "
        + "AND userId = :userId ")
    void deleteUserRoleByUserId(@Param("storeId") String storeId, @Param("userId") Integer userId);

    /**
     * 指定ユーザーIDの役割情報削除.
     *
     * @param storeId     店舗ID
     * @param userIdList  ユーザーIDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RUserRole t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.userId IN :userIdList ")
    void updateDelFlagByUserId(@Param("storeId") String storeId,
        @Param("userIdList") List<Integer> userIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定ユーザーIDの役割情報.
     *
     * @param storeId 店舗ID
     * @param userId  ユーザーID
     * @param delFlag 削除フラグ
     * @return 役割情報
     */
    List<RUserRole> findRUserRoleByStoreIdAndUserIdAndDelFlag(String storeId,
        Integer userId, Integer delFlag);
}
