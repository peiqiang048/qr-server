package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MControl;
import com.cnc.qr.common.entity.MStore;

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
 * コントロールマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MControlRepository extends JpaRepository<MControl, Long> {

    /**
     * 指定店舗コントロール情報取得.
     *
     * @param storeId     店舗ID
     * @param controlType 支払方式
     * @param delFlag     削除フラグ
     * @return コントロール情報
     */
    MControl findByStoreIdAndControlTypeAndDelFlag(String storeId, String controlType,
        Integer delFlag);


    /**
     * 指定支払方式ロック.
     *
     * @param storeId     店舗ID
     * @param controlType 支払方式
     * @return 支払方式情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT t1 "
        + "FROM MControl t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.controlType = :controlType "
        + "AND t1.delFlag = 0")
    MControl findByStoreIdAndControlTypeForLock(@Param("storeId") String storeId,
        @Param("controlType") String controlType);

    /**
     * 店舗支払方式更新.
     *
     * @param storeId     店舗ID
     * @param controlType 支払方式
     * @param controlCode 支払CODE
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE m_control "
        + "SET control_code = :controlCode,"
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "WHERE store_id = :storeId "
        + "AND control_type = :controlType "
        + "AND del_flag = 0", nativeQuery = true)
    Integer updateStorePaymentMethod(@Param("controlType") String controlType,
        @Param("controlCode") String controlCode, @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime, @Param("storeId") String storeId);


    /**
     * 指定店舗コントロール情報取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return コントロール情報
     */
    List<MControl> findByStoreIdAndDelFlag(String storeId,
        Integer delFlag);

}
