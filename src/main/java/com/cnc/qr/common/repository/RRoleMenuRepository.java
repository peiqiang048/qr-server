package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RRoleMenu;
import com.cnc.qr.core.pc.model.UserMenuInfoDataDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 役割メニュー関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RRoleMenuRepository extends JpaRepository<RRoleMenu, Long> {

    /**
     * ユーザメニュー情報取得.
     *
     * @param businessId ビジネスID
     * @param storeId    店舗ID
     * @param loginId    角色ID
     * @return メニュー情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.UserMenuInfoDataDto("
        + "t4.parentMenuId,"
        + "t5.menuName,"
        + "t5.menuIcon,"
        + "t4.childMenuId,"
        + "t6.menuName,"
        + "t6.menuLink,"
        + "t6.menuIcon) "
        + "FROM RRoleMenu t1 "
        + "INNER JOIN RUserRole t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.roleId = t2.roleId "
        + "INNER JOIN RUser t3 "
        + "ON t3.businessId = :businessId "
        + "AND t3.userId = t2.userId "
        + "AND t3.delFlag = 0 "

        + "INNER JOIN MStore t7 "
        + "ON t7.storeId = :storeId "
        + "AND t7.businessId = t3.businessId "
        + "AND t7.delFlag = 0 "

        + "LEFT JOIN RParentMenu t4 "
        + "ON t1.storeId = t4.storeId "
        + "AND t1.menuId = t4.childMenuId "
        + "AND t4.delFlag = 0 "
        + "LEFT JOIN MMenu t5 "
        + "ON t4.storeId = t5.storeId "
        + "AND t4.parentMenuId = t5.menuId "
        + "AND t5.delFlag = 0 "
        + "LEFT JOIN MMenu t6 "
        + "ON t4.storeId = t6.storeId "
        + "AND t4.childMenuId = t6.menuId "
        + "AND t6.delFlag = 0"
        + "WHERE t1.storeId = :storeId "
        + "AND t3.loginId = :loginId "
        + "AND t1.delFlag = 0"
        + "AND t2.delFlag = 0 "
        + "AND t3.delFlag = 0 ")
    List<UserMenuInfoDataDto> getMenuInfoByUserId(@Param("businessId") String businessId,
        @Param("storeId") String storeId,
        @Param("loginId") String loginId);

}
