package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.core.order.model.MenuDto;
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
 * ユーザテーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RUserRepository extends JpaRepository<RUser, Long> {

    /**
     * ログインユーザ権限情報取得.
     *
     * @param storeId      店舗ID
     * @param loginId      ログインID
     * @param userPassword パスワード
     * @return 権限情報
     */
    @Query(value = "SELECT t4.modelAuthorities "
        + "FROM RUser t1 "
        + "INNER JOIN MStore t5 "
        + "ON t5.storeId = :storeId "
        + "AND t5.businessId = t1.businessId "
        + "AND t5.delFlag = 0 "
        + "LEFT JOIN RUserRole t2 "
        + "ON t1.userId = t2.userId "
        + "AND t5.storeId = t2.storeId "
        + "AND t2.delFlag = 0 "
        + "LEFT JOIN RRoleModel t3 "
        + "ON t2.roleId = t3.roleId "
        + "AND t2.storeId = t3.storeId "
        + "AND t3.delFlag = 0 "
        + "LEFT JOIN RModel t4 "
        + "ON t3.modelId = t4.modelId "
        + "AND t3.storeId = t4.storeId "
        + "AND t4.delFlag = 0 "
        + "WHERE t1.loginId = :loginId "
        + "AND t1.userPassword = :userPassword "
        + "AND t1.delFlag = 0 ")
    List<String> findStoreUserRoleCode(@Param("storeId") String storeId,
        @Param("loginId") String loginId, @Param("userPassword") String userPassword);

    /**
     * ログインユーザ権限情報取得.
     *
     * @param storeId 店舗ID
     * @param loginId ログインID
     * @return 権限情報
     */
    @Query(value = "SELECT distinct t4.modelAuthorities "
        + "FROM RUser t1 "
        + "INNER JOIN MStore t5 "
        + "ON t5.storeId = :storeId "
        + "AND t5.businessId = t1.businessId "
        + "AND t5.delFlag = 0 "
        + "LEFT JOIN RUserRole t2 "
        + "ON t1.userId = t2.userId "
        + "AND t5.storeId = t2.storeId "
        + "AND t2.delFlag = 0 "
        + "LEFT JOIN RRoleModel t3 "
        + "ON t2.roleId = t3.roleId "
        + "AND t2.storeId = t3.storeId "
        + "AND t3.delFlag = 0 "
        + "LEFT JOIN RModel t4 "
        + "ON t3.modelId = t4.modelId "
        + "AND t3.storeId = t4.storeId "
        + "AND t4.delFlag = 0 "
        + "WHERE t1.loginId = :loginId "
        + "AND t1.delFlag = 0 ")
    List<String> findChangeStoreUserRoleCode(@Param("storeId") String storeId,
        @Param("loginId") String loginId);

    /**
     * ログインユーザ権限情報取得.
     *
     * @param businessId   ビジネスID
     * @param storeId      店舗ID
     * @param loginId      ログインID
     * @param userPassword パスワード
     * @return 権限情報
     */
    @Query(value = "SELECT t4.modelAuthorities "
        + "FROM RUser t1 "
        + "INNER JOIN MStore t5 "
        + "ON t5.businessId = t1.businessId "
        + "AND t5.delFlag = 0 "
        + "INNER JOIN RUserRole t2 "
        + "ON t1.userId = t2.userId "
        + "AND t5.storeId = t2.storeId "
        + "AND t2.delFlag = 0 "
        + "AND t2.storeId = :storeId "
        + "INNER JOIN RRoleModel t3 "
        + "ON t2.roleId = t3.roleId "
        + "AND t2.storeId = t3.storeId "
        + "AND t3.delFlag = 0 "
        + "INNER JOIN RModel t4 "
        + "ON t3.modelId = t4.modelId "
        + "AND t3.storeId = t4.storeId "
        + "AND t4.delFlag = 0 "
        + "WHERE t1.loginId = :loginId "
        + "AND t1.userPassword = :userPassword "
        + "AND t1.businessId = :businessId "
        + "AND t1.delFlag = 0 ")
    List<String> findBusinessUserRoleCode(@Param("businessId") String businessId,
        @Param("loginId") String loginId, @Param("userPassword") String userPassword,
        @Param("storeId") String storeId);

    /**
     * ログインユーザ権限情報取得.
     *
     * @param storeId  店舗ID
     * @param loginId  ログインID
     * @param menuType メニュー区分
     * @return 権限情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.MenuDto ( " +
        "t4.menuId, " +
        "t4.menuName, " +
        "t4.menuLink, " +
        "t4.menuIcon) " +
        "FROM RUser t1 " +
        "INNER JOIN MStore t5 " +
        "ON t5.storeId = :storeId " +
        "AND t5.businessId = t1.businessId " +
        "AND t5.delFlag = 0 " +

        "INNER JOIN RUserRole t2 " +
        "ON t1.userId = t2.userId " +
        "AND t5.storeId = t2.storeId " +
        "AND t2.delFlag = 0 " +

        "INNER JOIN RRole t6 " +
        "ON t6.roleId = t2.roleId " +
        "AND t6.storeId = t2.storeId " +
        "AND t6.delFlag = 0 " +

        "INNER JOIN RRoleModel t7 " +
        "ON t7.roleId = t6.roleId " +
        "AND t7.storeId = t6.storeId " +
        "AND t7.delFlag = 0 " +

        "INNER JOIN RModel t8 " +
        "ON t8.modelId = t7.modelId " +
        "AND t8.storeId = t7.storeId " +
        "AND t8.delFlag = 0 " +

        "INNER JOIN RMenuModel t9 " +
        "ON t9.modelId = t8.modelId " +
        "AND t9.storeId = t8.storeId " +
        "AND t9.delFlag = 0 " +

        "INNER JOIN MMenu t4 " +
        "ON t4.menuId = t9.menuId " +
        "AND t9.storeId = t4.storeId " +
        "AND t4.delFlag = 0 " +
        "AND t4.menuType = :menuType " +
        "WHERE  t1.loginId = :loginId " +
        "AND t1.delFlag = 0 " +
        "order by t4.sortOrder ASC")
    List<MenuDto> findUserMenu(@Param("storeId") String storeId,
        @Param("loginId") String loginId,
        @Param("menuType") String menuType);

    /**
     * ユーザ情報取得.
     *
     * @param storeId            店舗ID
     * @param userClassification ユーザ区分リスト
     * @return ユーザ情報
     */
    @Query(value = "SELECT DISTINCT t1.user_id, "
        + "t1.login_id AS login, "
        + "t1.user_name AS userName "
        + "FROM r_user t1 "
        + "INNER JOIN  m_store t2 "
        + "ON  t2.business_id = t1.business_id "
        + "AND  t2.del_flag = 0 "
        + "AND  t2.store_id = :storeId "

        + "INNER JOIN  r_user_role t4 "
        + "ON  t4.store_id = t2.store_id "
        + "AND  t4.del_flag = 0 "
        + "AND  t4.user_id = t1.user_id "
        + "AND  t4.user_classification in (:userClassification)"

        + "WHERE  t1.del_flag = 0 "
        + "ORDER BY t1.user_id ASC ", nativeQuery = true)
    List<Map<String, Object>> getStoreIdAndUser(@Param("storeId") String storeId,
        @Param("userClassification") List<String> userClassification);

    /**
     * 店舗ユーザ情報取得.
     *
     * @param businessId                  ビジネスID
     * @param storeId                     店舗ID
     * @param pageable                    ページ情報
     * @param codeGroupUserClassification ユーザ区分
     * @param codeGroupStopFlag           停用标识
     * @param userClassification          ユーザ区分
     * @return ユーザ情報
     */
    @Query(value =
        "select  ROW_NUMBER() OVER(ORDER BY ts.userId ASC) AS num, "
            + "ts.userId, "
            + "ts.loginId, "
            + "ts.userName, "
            + "ts.version, "
            + "ts.lastLoginTime, "
            + "ts.stopFlag, "
            + "ts.userClassification, "
            + "ts.stopFlag1 from ("
            + "SELECT t1.user_id AS userId, "
            + "t1.login_id AS loginId, "
            + "t1.user_name AS userName, "
            + "t1.version AS version, "
            + "t1.user_last_login_time AS lastLoginTime, "
            + "t2.store_name AS stopFlag, "
            + "t3.code_name AS userClassification, "
            + "t4.code_name AS stopFlag1 "
            + "FROM r_user t1 "
            + "INNER JOIN  m_store t2 "
            + "ON  t2.business_id = t1.business_id "
            + "AND  t2.del_flag = 0 "
            + "AND  t2.store_id = :storeId "
            + "INNER JOIN (SELECT DISTINCT store_id, user_id, user_classification "
            + "FROM r_user_role WHERE store_id = :storeId "
            + "AND user_classification <> :userClassification AND del_flag = 0 ) t5 "
            + "ON  t5.store_id = t2.store_id "
            + "AND  t5.user_id = t1.user_id "
            + "INNER JOIN m_code t3 "
            + "ON t2.store_id = t3.store_id "
            + "AND t3.code_group = :codeGroupUserClassification "
            + "AND t3.code = t5.user_classification "
            + "AND t3.del_flag = 0 "
            + "INNER JOIN m_code t4 "
            + "ON t2.store_id = t4.store_id "
            + "AND t4.code_group = :codeGroupStopFlag "
            + "AND t4.code = cast(t1.stop_flag as varchar(2)) "
            + "AND t4.del_flag = 0 "
            + "WHERE t1.business_id = :businessId "
            + "AND t1.del_flag = 0 "
            + "ORDER BY t1.user_id ASC) ts ", nativeQuery = true)
    Page<Map<String, Object>> findUserByStoreId(@Param("businessId") String businessId,
        @Param("storeId") String storeId, Pageable pageable,
        @Param("codeGroupUserClassification") String codeGroupUserClassification,
        @Param("codeGroupStopFlag") String codeGroupStopFlag,
        @Param("userClassification") String userClassification);

    /**
     * 店舗ユーザ情報取得.
     *
     * @param businessId                  ビジネスID
     * @param pageable                    ページ情報
     * @param codeGroupUserClassification ユーザ区分
     * @param codeGroupStopFlag           停用标识
     * @return ユーザ情報
     */
    @Query(value = "select   ROW_NUMBER() OVER(ORDER BY ts.userId ASC) AS num, "
        + "ts.userId, "
        + "ts.loginId, "
        + "ts.userName, "
        + "ts.version , "
        + "ts.lastLoginTime , "
        + "ts.userClassification, "
        + "ts.stopFlag "

        + "  From ( "
        + "SELECT  DISTINCT "
        + "t1.user_id AS userId,  "
        + "t1.login_id AS loginId,  "
        + "t1.user_name AS userName,  "
        + "t1.version AS version,  "
        + "t1.user_last_login_time AS lastLoginTime,  "
        + "case when t5.role_id = -1 then 'Business' else  t2.store_name end AS stopFlag,  "
        + "t3.code_name AS userClassification,  "
        + "t4.code_name AS stopFlag1 "
        + "FROM r_user t1  "
        + "INNER JOIN  m_store t2  "
        + "ON  t2.business_id = t1.business_id  "
        + "AND  t2.del_flag = 0  "
        + "INNER JOIN  r_user_role t5  "
        + "ON  t5.store_id = t2.store_id "
        + "AND  t5.del_flag = 0  "
        + "AND  t5.user_id = t1.user_id  "
        + "INNER JOIN m_code t3  "
        + "ON t2.store_id = t3.store_id  "
        + "AND t3.code_group = :codeGroupUserClassification  "
        + "AND t3.code = t5.user_classification  "
        + "AND t3.del_flag = 0  "
        + "INNER JOIN m_code t4  "
        + "ON t2.store_id = t4.store_id  "
        + "AND t4.code_group = :codeGroupStopFlag "
        + "AND t4.code = cast(t1.stop_flag as varchar(2))  "
        + "AND t4.del_flag = 0  "
        + "WHERE t1.business_id = :businessId  "
        + "AND t1.del_flag = 0  "
        + "ORDER BY t1.user_id ASC  ) ts", nativeQuery = true)
    Page<Map<String, Object>> findBusinessUserByStoreId(@Param("businessId") String businessId,
        Pageable pageable,
        @Param("codeGroupUserClassification") String codeGroupUserClassification,
        @Param("codeGroupStopFlag") String codeGroupStopFlag);

    /**
     * ユーザ情報取得.
     *
     * @param businessId ビジネスID
     * @param loginId    ログインID
     * @param delFlag    削除フラグ
     * @return ユーザ情報
     */
    List<RUser> findByBusinessIdAndLoginIdAndDelFlag(String businessId, String loginId,
        Integer delFlag);

    /**
     * 指定ユーザーIDのユーザ情報取得.
     *
     * @param businessId ビジネスID
     * @param userId     ユーザーID
     * @param delFlag    削除フラグ
     * @return ユーザ情報
     */
    RUser findByBusinessIdAndUserIdAndDelFlag(String businessId, Integer userId, Integer delFlag);

    /**
     * 指定ユーザーIDのユーザ情報ロック.
     *
     * @param businessId ビジネスID
     * @param userId     ユーザーID
     * @return ユーザ情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM RUser t1 "
        + "WHERE t1.businessId = :businessId "
        + "AND t1.userId = :userId "
        + "AND t1.delFlag = 0")
    RUser findByUserIdForLock(@Param("businessId") String businessId,
        @Param("userId") Integer userId);

    /**
     * 指定ユーザーID削除.
     *
     * @param businessId  ビジネスID
     * @param userIdList  ユーザーIDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE RUser t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.businessId = :businessId "
        + "AND t1.userId IN :userIdList ")
    void updateDelFlagByUserId(@Param("businessId") String businessId,
        @Param("userIdList") List<Integer> userIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定ユーザパスワードリセット.
     *
     * @param businessId   ビジネスID
     * @param userIdList   ユーザーIDリスト
     * @param userPassword パスワード
     * @param updOperCd    更新者
     * @param updDateTime  更新日時
     */
    @Modifying
    @Query(value = "UPDATE RUser t1 "
        + "SET t1.userPassword = :userPassword, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.businessId = :businessId "
        + "AND t1.userId IN :userIdList ")
    void resetUserPasswordByUserId(@Param("businessId") String businessId,
        @Param("userIdList") List<Integer> userIdList,
        @Param("userPassword") String userPassword,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * ログインユーザ情報取得.
     *
     * @param businessId   ビジネスID
     * @param userId       ユーザーID
     * @param userPassword パスワード
     * @param delFlag      削除フラグ
     * @return ユーザ情報
     */
    RUser findByBusinessIdAndUserIdAndUserPasswordAndDelFlag(String businessId, Integer userId,
        String userPassword, Integer delFlag);

    /**
     * ユーザ情報取得.
     *
     * @param businessId ビジネスID
     * @param userId     ユーザーID
     * @return ユーザ情報
     */
    @Query(value = "SELECT t2.store_id, t2.store_name, t3.user_classification "
        + "FROM r_user t1 "

        + "INNER JOIN m_store t2 "
        + "ON t2.business_id = t1.business_id "
        + "AND t2.del_flag = 0 "

        + "INNER JOIN r_user_role t3 "
        + "ON t3.store_id = t2.store_id "
        + "AND t3.user_id = t1.user_id "
        + "AND t3.del_flag = 0 "

        + "WHERE t1.business_id = :businessId "
        + "AND t1.user_id = :userId "
        + "AND t1.del_flag = 0", nativeQuery = true)
    List<Map<String, Object>> findStoreByUserId(@Param("businessId") String businessId,
        @Param("userId") Integer userId);


}
