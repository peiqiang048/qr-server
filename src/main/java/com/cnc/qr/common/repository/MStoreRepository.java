package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MStore;
import java.math.BigDecimal;
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
 * 店舗マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MStoreRepository extends JpaRepository<MStore, Long> {

    /**
     * 指定店舗情報取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 店舗情報
     */
    MStore findByStoreIdAndDelFlag(String storeId, Integer delFlag);


    /**
     * 店舗情報取得.
     *
     * @param businessId ビジネスID
     * @param delFlag    削除フラグ
     * @return 店舗情報
     */
    List<MStore> findByBusinessIdAndDelFlagOrderByStoreIdAsc(String businessId, Integer delFlag);

    /**
     * すべて店舗情報取得.
     *
     * @param businessId ビジネスID
     * @param delFlag    削除フラグ
     * @return 店舗情報
     */
    List<MStore> findByBusinessIdAndDelFlag(String businessId, Integer delFlag);

    /**
     * 店舗一覧情報取得.
     *
     * @param loginId     ログインID
     * @param paymentDiv  支払方式
     * @param storeIdList 店舗IDリスト
     * @return 店舗情報
     */
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY t1.store_id ASC) AS num,"
        + "t1.store_id AS storeId, "
        + "t1.store_name AS storeName, "
        + "t1.start_time AS openStoreTime, "
        + "t1.end_time AS closeStoreTime, "
        + "t1.store_phone AS tel, "
        + "t1.post_number AS postNumber, "
        + "t3.control_code AS paymentTerminal "
        + "FROM m_store t1 "
        + "INNER JOIN r_user t2 "
        + "ON t2.business_id = t1.business_id "
        + "INNER JOIN m_control t3 "
        + "ON t3.store_id = t1.store_id "
        + "AND t3.control_type = :paymentDiv "
        + "WHERE t2.login_id = :loginId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "AND t1.store_id in :storeIdList "
        + "ORDER BY t1.store_id ASC", nativeQuery = true)
    List<Map<String, Object>> findStoreList(@Param("loginId") String loginId,
        @Param("paymentDiv") String paymentDiv, @Param("storeIdList") List<String> storeIdList);


    /**
     * 店舗情報取得.
     *
     * @param storeId    店舗ID
     * @param paymentDiv 支払方式
     * @return 店舗情報
     */
    @Query(value = "SELECT " +
        "t1.store_name AS storeName, " +
        "t1.store_name_ktkn AS storeNameKatakana, " +
        "t1.store_phone AS tel, " +
        "t1.post_number AS postNumber, " +
        "t1.fax_number AS fax, " +
        "t1.start_time AS openStoreTime, " +
        "t1.end_time AS closeStoreTime, " +
        "t1.order_end_time AS orderEndTime, " +
        "t1.store_if AS storeIntrodution, " +
        "t1.store_address AS storeAddress, " +
        "t2.control_code AS paymentTerminal, " +
        "t1.staff_check AS staffCheckFlag, " +
        "t1.course_buffet_check AS courseBuffetCheck, " +
        "t1.default_use_time AS defaultUseTime " +
        "FROM m_store t1 " +
        "INNER JOIN m_control t2 " +
        "ON t2.store_id = t1.store_id " +
        "AND t2.control_type = :paymentDiv " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findStoreInfo(@Param("storeId") String storeId,
        @Param("paymentDiv") String paymentDiv);

    /**
     * 指定店舗情報ロック.
     *
     * @param storeId 店舗ID
     * @return 店舗情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MStore t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.delFlag = 0")
    MStore findByStoreIdForLock(@Param("storeId") String storeId);

    /**
     * 店舗情報更新.
     *
     * @param storeId           店舗ID
     * @param storeName         店舗名
     * @param storeNameKtkn     店舗名カタカナ
     * @param storeIntrodution  店舗紹介
     * @param storeAddress      店舗住所
     * @param storePhone        店舗電話
     * @param postNumber        郵便番号
     * @param faxNumber         FAX番号
     * @param startTime         営業開始時間
     * @param endTime           営業締め時間
     * @param orderEndTime      注文締め時間
     * @param staffCheck        店員確認要否
     * @param courseBuffetCheck コース放題確認要否
     * @param defaultUseTime    ディフォルト利用時間
     * @param updOperCd         更新者
     * @param updDateTime       更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE m_store "
        + "SET store_name = :storeName,"
        + "store_name_ktkn = :storeNameKtkn,"
        + "store_if = :storeIntrodution, "
        + "store_address = :storeAddress, "
        + "store_phone = :storePhone, "
        + "post_number = :postNumber, "
        + "fax_number = :faxNumber, "
        + "start_time = :startTime, "
        + "end_time = :endTime, "
        + "order_end_time = :orderEndTime, "
        + "staff_check = :staffCheck, "
        + "course_buffet_check = :courseBuffetCheck, "
        + "default_use_time = :defaultUseTime, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId AND del_flag = 0", nativeQuery = true)
    Integer updateStore(@Param("storeName") String storeName,
        @Param("storeNameKtkn") String storeNameKtkn,
        @Param("storeIntrodution") String storeIntrodution,
        @Param("storeAddress") String storeAddress,
        @Param("storePhone") String storePhone, @Param("postNumber") String postNumber,
        @Param("faxNumber") String faxNumber, @Param("startTime") String startTime,
        @Param("endTime") String endTime, @Param("orderEndTime") String orderEndTime,
        @Param("staffCheck") String staffCheck,
        @Param("courseBuffetCheck") String courseBuffetCheck,
        @Param("defaultUseTime") BigDecimal defaultUseTime, @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime, @Param("storeId") String storeId);

    /**
     * 店舗情報検証.
     *
     * @param storeId       店舗ID
     * @param storePassword パスワード
     * @param delFlag       削除フラグ
     * @return 店舗情報
     */
    MStore findByStoreIdAndStorePasswordAndDelFlag(String storeId, String storePassword,
        Integer delFlag);

    /**
     * ディフォルト利用時間情報取得.
     *
     * @param storeId 店舗ID
     * @return ディフォルト利用時間
     */
    @Query(value = "SELECT " +
        "t1.default_use_time AS defaultUseTime " +
        "FROM m_store t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findDefaultUseTimeByStoreId(@Param("storeId") String storeId);

    /**
     * 出前設定取得.
     *
     * @param storeId 店舗ID
     * @return 出前設定情報
     */
    @Query(value = "SELECT ms.catering_interval_time as cateringIntervalTime, " +
        "ms.takeout_interval_time as takeoutIntervalTime, " +
        "ms.interval_time as intervalTime, " +
        "ms.delivery_type_flag as deliveryTypeFlag, " +
        "ms.delivery_flag as deliveryFlag, " +
        "ms.catering_charge_type as cateringPriceType, " +
        "ms.catering_charge_amount as cateringAmount, " +
        "ms.catering_charge_percent as cateringPercent  " +
        "FROM m_store ms " +
        "where ms.store_id = :storeId " +
        "and ms.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> getDeliverySetting(@Param("storeId") String storeId);

    /**
     * 店舗情報更新(出前設定用).
     *
     * @param storeId              店舗ID
     * @param cateringIntervalTime 配達時間
     * @param takeoutIntervalTime  持帰り時間
     * @param intervalTime         間隔時間
     * @param deliveryTypeFlag     出前仕方フラグ
     * @param deliveryFlag         出前フラグ
     * @param cateringPriceType    配達料区分
     * @param cateringAmount       金額配達料
     * @param cateringPercent      百分率配達料
     * @param updOperCd            更新者
     * @param updDateTime          更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE m_store "
        + "SET catering_interval_time = :cateringIntervalTime,"
        + "takeout_interval_time = :takeoutIntervalTime,"
        + "interval_time = :intervalTime, "
        + "delivery_type_flag = :deliveryTypeFlag, "
        + "delivery_flag = :deliveryFlag, "
        + "catering_charge_type = :cateringPriceType, "
        + "catering_charge_amount = :cateringAmount, "
        + "catering_charge_percent = :cateringPercent, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId AND del_flag = 0", nativeQuery = true)
    Integer updateDeliveryStore(
        @Param("cateringIntervalTime") Integer cateringIntervalTime,
        @Param("takeoutIntervalTime") Integer takeoutIntervalTime,
        @Param("intervalTime") Integer intervalTime,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("deliveryFlag") String deliveryFlag,
        @Param("cateringPriceType") String cateringPriceType,
        @Param("cateringAmount") BigDecimal cateringAmount,
        @Param("cateringPercent") BigDecimal cateringPercent,
        @Param("updOperCd") String updOperCd, @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("storeId") String storeId);
}
