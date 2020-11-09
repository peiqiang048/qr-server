package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MPrint;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * プリンタマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrintRepository extends JpaRepository<MPrint, Long> {

    /**
     * プリンターリスト取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return プリンターリスト
     */
    List<MPrint> findByStoreIdAndDelFlag(String storeId, Integer delFlag);

    /**
     * キッチンプリンタ情報取得.
     *
     * @param storeId   店舗ID
     * @param kitchenId キッチンプリンタID
     * @return キッチンプリンタ情報
     */
    @Query(value = "SELECT " +
        "t3.print_id AS printId " +
        "FROM m_kitchen t1 " +
        "INNER JOIN r_kitchen_print t2 " +
        "ON t2.store_id = t1.store_id " +
        "AND t2.kitchen_id = t1.kitchen_id " +
        "AND t2.del_flag = 0 " +
        "INNER JOIN m_print t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.print_id = t2.print_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.kitchen_id = :kitchenId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findKitchenPrintInfo(@Param("storeId") String storeId,
        @Param("kitchenId") Integer kitchenId);

    /**
     * プリンター一覧情報取得.
     *
     * @param storeId             店舗ID
     * @param codeGroupBrand      プリンターブランド
     * @param codeGroupConnection プリンター接続方式
     * @param printSize           プリンター印紙幅
     * @return プリンター一覧情報
     */
    @Query(value = "SELECT " +
        "t1.print_id AS printId, " +
        "t1.print_name AS printName, " +
        "t1.print_ip AS printIp, " +
        "t1.bluetooth_name AS blueToothName, " +
        "t2.code_name AS brandName, " +
        "t4.code_name AS printSize, " +
        "t1.print_model_no AS printModel, " +
        "t3.code_name AS connectionMethodName  " +
        "FROM m_print t1 " +
        "LEFT JOIN m_code t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t2.code_group = :codeGroupBrand " +
        "AND t2.code = t1.brand_code " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_code t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t3.code_group = :codeGroupConnection " +
        "AND t3.code = t1.connection_method_code " +
        "AND t3.del_flag = 0 " +

        "LEFT JOIN m_code t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t4.code_group = :printSize " +
        "AND t4.code = t1.print_size " +
        "AND t4.del_flag = 0 " +

        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findPrintInfoList(@Param("storeId") String storeId,
        @Param("codeGroupBrand") String codeGroupBrand,
        @Param("codeGroupConnection") String codeGroupConnection,
        @Param("printSize") String printSize);

    /**
     * プリンター情報取得.
     *
     * @param storeId 店舗ID
     * @param printId プリンターID
     * @return プリンター情報
     */
    @Query(value = "SELECT " +
        "t1.print_name AS printName, " +
        "t1.brand_code AS brandCode, " +
        "t1.connection_method_code AS connectionMethodCode, " +
        "t1.print_ip AS printIp, " +
        "t1.bluetooth_name AS blueToothName, " +
        "t1.print_model_no AS printModel, " +
        "t1.print_size AS printSizeCode  " +
        "FROM m_print t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.print_id = :printId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findPrintInfo(@Param("storeId") String storeId,
        @Param("printId") Integer printId);

    /**
     * プリンター更新.
     *
     * @param printName            プリンタ名
     * @param brandCode            プリンタブランドCD
     * @param connectionMethodCode 接続方式CD
     * @param printIp              プリンタip
     * @param blueToothName        ブルートゥース名
     * @param printModel           モデル
     * @param printSizeCode        チケット幅CD
     * @param updOperCd            更新者
     * @param updDateTime          更新日時
     * @param storeId              店舗ID
     * @param printId              プリンタID
     */
    @Modifying
    @Query(value = ""
        + "UPDATE m_print "
        + "SET print_name = :printName,"
        + "brand_code = :brandCode, "
        + "connection_method_code = :connectionMethodCode, "
        + "print_ip = :printIp, "
        + "bluetooth_name = :blueToothName, "
        + "print_model_no = :printModel, "
        + "print_size = :printSizeCode, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1  "
        + "WHERE store_id = :storeId "
        + "AND del_flag = 0 "
        + "AND print_id = :printId ", nativeQuery = true)
    Integer printUpdate(@Param("printName") String printName, @Param("brandCode") String brandCode,
        @Param("connectionMethodCode") String connectionMethodCode,
        @Param("printIp") String printIp,
        @Param("blueToothName") String blueToothName, @Param("printModel") String printModel,
        @Param("printSizeCode") String printSizeCode, @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime, @Param("storeId") String storeId,
        @Param("printId") Integer printId);

    /**
     * 指定プリンターID削除.
     *
     * @param storeId     店舗ID
     * @param printIdList プリンタIDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE m_print "
        + "SET del_flag = 1, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId "
        + "AND print_id IN :printIdList ", nativeQuery = true)
    void updateDelFlagByprintId(@Param("storeId") String storeId,
        @Param("printIdList") List<Integer> printIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

}
