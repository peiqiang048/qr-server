package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RRoleModel;
import com.cnc.qr.core.pc.model.ModelInfoDto;
import com.cnc.qr.core.pc.model.RoleInfoDto;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 役割関連機能テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RRoleModelRepository extends JpaRepository<RRoleModel, Long> {

    /**
     * 指定役割の機能情報取得.
     *
     * @param storeId 店舗ID
     * @param roleId  角色ID
     * @return 機能情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.ModelInfoDto ( "
        + "t1.modelId, "
        + "t2.modelName) "
        + "FROM RRoleModel t1 "
        + "INNER JOIN RModel t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.modelId = t2.modelId "
        + "WHERE t1.storeId = :storeId "
        + "AND (t1.roleId = :roleId or :roleId = -1) "
        + "AND t1.delFlag = 0 ")
    List<ModelInfoDto> findModelByRoleId(@Param("storeId") String storeId,
        @Param("roleId") Integer roleId);


    /**
     * 指定役割IDの関連機能情報削除.
     *
     * @param storeId 店舗ID
     * @param roleId  角色ID
     */
    @Modifying
    @Query(value = ""
        + "DELETE FROM RRoleModel "
        + "WHERE storeId = :storeId "
        + "AND roleId = :roleId ")
    void deleteRoleModelByRoleId(@Param("storeId") String storeId, @Param("roleId") Integer roleId);

    /**
     * 指定役割IDの関連機能削除.
     *
     * @param storeId     店舗ID
     * @param roleIdList  角色IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RRoleModel t1 "
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

}
