package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MCode;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * CODEマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MCodeRepository extends JpaRepository<MCode, Long> {

    /**
     * オプション種類区分情報取得.
     *
     * @param storeId   店舗ID
     * @param groupCode オプション種類区分
     * @return オプション種類区分情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.ClassificationInfoDto(" +
        "t1.code," +
        "t1.codeName) " +
        "from MCode t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.codeGroup = :groupCode " +
        "AND t1.delFlag = 0 ")
    List<ClassificationInfoDto> getClassificationList(@Param("storeId") String storeId,
        @Param("groupCode") String groupCode);

    /**
     * 媒体用途情報取得.
     *
     * @param storeId  店舗ID
     * @param mediaUse 媒体用途
     * @return 媒体用途情報
     */
    @Query(value = "SELECT " +
        "t1.code AS useKey, " +
        "t1.code_name AS useText " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :mediaUse " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findMediaUseInfo(@Param("storeId") String storeId,
        @Param("mediaUse") String mediaUse);

    /**
     * 支払手段情報取得.
     *
     * @param storeId   店舗ID
     * @param delFlag   削除フラグ
     * @param codeGroup 支払方式
     * @param code      支払方式CD
     * @return 支払手段情報
     */
    MCode findByStoreIdAndDelFlagAndCodeGroupAndCode(String storeId, Integer delFlag,
        String codeGroup, String code);

    /**
     * プリンターブランド情報取得.
     *
     * @param storeId               店舗ID
     * @param printerBrandGroupCode プリンターブランド
     * @return プリンターブランド情報
     */
    @Query(value = "SELECT " +
        "t1.code AS brandCode, " +
        "t1.code_name AS brandName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :printerBrandGroupCode " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findBrandInfo(@Param("storeId") String storeId,
        @Param("printerBrandGroupCode") String printerBrandGroupCode);

    /**
     * プリンター接続方法情報取得.
     *
     * @param storeId                          店舗ID
     * @param printerConnectionMethodGroupCode プリンター接続方式
     * @return プリンター接続方法情報
     */
    @Query(value = "SELECT " +
        "t1.code AS connectionMethodCode, " +
        "t1.code_name AS connectionMethodName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :printerConnectionMethodGroupCode " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findConnectionMethodInfo(@Param("storeId") String storeId,
        @Param("printerConnectionMethodGroupCode") String printerConnectionMethodGroupCode);

    /**
     * プリンターチケット幅情報取得.
     *
     * @param storeId            店舗ID
     * @param printSizeGroupCode プリンター印紙幅
     * @return プリンターチケット幅情報
     */
    @Query(value = "SELECT " +
        "t1.code AS printSizeCode, " +
        "t1.code_name AS printSize " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :printSizeGroupCode " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findPrintSizeInfo(@Param("storeId") String storeId,
        @Param("printSizeGroupCode") String printSizeGroupCode);

    /**
     * 席種類情報取得.
     *
     * @param storeId            店舗ID
     * @param tableTypeGroupCode テーブル区分
     * @return 席種類情報
     */
    @Query(value = "SELECT " +
        "t1.code AS tableTypeId, " +
        "split_part(t1.code_name, ',', 1) AS tableTypeName, " +
        "split_part(t1.code_name, ',', 2) AS tableColor " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :tableTypeGroupCode " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findTableTypeInfo(@Param("storeId") String storeId,
        @Param("tableTypeGroupCode") String tableTypeGroupCode);

    /**
     * 出前仕方フラグ情報取得.
     *
     * @param storeId                   店舗ID
     * @param deliveryTypeFlagCodeGroup 注文出前仕方フラグ
     * @return 出前仕方フラグ情報
     */
    @Query(value = "SELECT " +
        "t1.code AS deliveryTypeFlagCd, " +
        "t1.code_name AS deliveryTypeFlagName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :deliveryTypeFlagCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findDeliveryTypeFlagInfo(@Param("storeId") String storeId,
        @Param("deliveryTypeFlagCodeGroup") String deliveryTypeFlagCodeGroup);

    /**
     * 出前状態情報取得.
     *
     * @param storeId         店舗ID
     * @param statusCodeGroup 出前状態
     * @return 出前状態情報
     */
    @Query(value = "SELECT " +
        "t1.code AS statusCd, " +
        "t1.code_name AS statusName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :statusCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findDeliveryStatusInfo(@Param("storeId") String storeId,
        @Param("statusCodeGroup") String statusCodeGroup);

    /**
     * 出前仕方フラグ情報取得(PC商品取得用).
     *
     * @param storeId                   店舗ID
     * @param deliveryTypeFlagCodeGroup 注文出前仕方フラグ
     * @return 出前状態情報
     */
    @Query(value = "SELECT " +
        "t1.code AS cateringTypeFlag, " +
        "t1.code_name AS cateringTypeFlagName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :deliveryTypeFlagCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findCateringTypeInfo(@Param("storeId") String storeId,
        @Param("deliveryTypeFlagCodeGroup") String deliveryTypeFlagCodeGroup);

    /**
     * 出前区分フラグ情報取得(PC商品取得用).
     *
     * @param storeId               店舗ID
     * @param deliveryFlagCodeGroup 出前区分
     * @return 出前区分フラグ情報
     */
    @Query(value = "SELECT " +
        "t1.code AS deliveryFlag, " +
        "t1.code_name AS deliveryFlagName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :deliveryFlagCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findDeliveryFlagInfo(@Param("storeId") String storeId,
        @Param("deliveryFlagCodeGroup") String deliveryFlagCodeGroup);

    /**
     * 出前仕方フラグ情報取得(出前設定取得用).
     *
     * @param storeId                   店舗ID
     * @param deliveryTypeFlagCodeGroup 設定出前仕方フラグ
     * @return 出前仕方フラグ情報
     */
    @Query(value = "SELECT " +
        "t1.code AS deliveryTypeFlagCd, " +
        "t1.code_name AS deliveryTypeFlagName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :deliveryTypeFlagCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findCateringTypeListInfo(@Param("storeId") String storeId,
        @Param("deliveryTypeFlagCodeGroup") String deliveryTypeFlagCodeGroup);

    /**
     * 出前区分フラグ情報取得(出前設定取得用).
     *
     * @param storeId               店舗ID
     * @param deliveryFlagCodeGroup 出前区分
     * @return 出前区分フラグ情報
     */
    @Query(value = "SELECT " +
        "t1.code AS deliveryFlagCd, " +
        "t1.code_name AS deliveryFlagName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :deliveryFlagCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findDeliveryFlagListInfo(@Param("storeId") String storeId,
        @Param("deliveryFlagCodeGroup") String deliveryFlagCodeGroup);

    /**
     * 配達料区分リスト情報取得.
     *
     * @param storeId                    店舗ID
     * @param cateringPriceTypeCodeGroup 配達料区分
     * @return 配達料区分リスト
     */
    @Query(value = "SELECT " +
        "t1.code AS cateringPriceTypeCd, " +
        "t1.code_name AS cateringPriceTypeName " +
        "FROM m_code t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.code_group = :cateringPriceTypeCodeGroup " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t1.code ASC", nativeQuery = true)
    List<Map<String, Object>> findCateringPriceTypeListInfo(@Param("storeId") String storeId,
        @Param("cateringPriceTypeCodeGroup") String cateringPriceTypeCodeGroup);
}
