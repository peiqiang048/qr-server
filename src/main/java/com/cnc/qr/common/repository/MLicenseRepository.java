package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MLicense;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.core.order.model.StoreLanguageDto;
import com.cnc.qr.core.pc.model.LanguageInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ライセンスマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MLicenseRepository extends JpaRepository<MLicense, Long> {

    /**
     * 指定店舗言語情報取得.
     *
     * @param storeId     店舗ID
     * @param licenseCode ライセンスCD
     * @return 言語情報
     */
    @Query(value =
        "SELECT new com.cnc.qr.core.order.model.StoreLanguageDto(t1.licenseCode, t2.codeName) "
            + "FROM MLicense t1 "
            + "LEFT JOIN MCode t2 "
            + "ON t1.storeId = t2.storeId "
            + "AND t1.licenseType = t2.codeGroup "
            + "AND t1.licenseCode = t2.code "
            + "AND t2.delFlag = 0 "
            + "WHERE t1.storeId = :storeId "
            + "AND t1.licenseType = :licenseCode "
            + "AND t1.delFlag = 0 "
            + "ORDER BY t1.licenseCode ASC")
    List<StoreLanguageDto> findStoreLanguageByCode(@Param("storeId") String storeId,
        @Param("licenseCode") String licenseCode);

    /**
     * 指定店舗支払方式情報取得.
     *
     * @param storeId     店舗ID
     * @param licenseType 種類CD
     * @return 支払方式情報
     */
    @Query(value =
        "SELECT new com.cnc.qr.core.order.model.PaymentMethodDto(t1.licenseCode, t2.codeName) "
            + "FROM MLicense t1 "
            + "LEFT JOIN MCode t2 "
            + "ON t1.storeId = t2.storeId "
            + "AND t1.licenseType = t2.codeGroup "
            + "AND t1.licenseCode = t2.code "
            + "AND t2.delFlag = 0 "
            + "WHERE t1.storeId = :storeId "
            + "AND t1.licenseType = :licenseType "
            + "AND t1.delFlag = 0 "
            + "ORDER BY t1.licenseCode ASC")
    List<PaymentMethodDto> findStorePaymentByType(@Param("storeId") String storeId,
        @Param("licenseType") String licenseType);

    /**
     * 指定支払方式情報取得.
     *
     * @param storeId     店舗ID
     * @param licenseType 種類CD
     * @param licenseCode ライセンスCD
     * @return 支払方式情報
     */
    @Query(value =
        "SELECT new com.cnc.qr.core.order.model.PaymentMethodDto(t1.licenseCode, t2.codeName) "
            + "FROM MLicense t1 "
            + "LEFT JOIN MCode t2 "
            + "ON t1.storeId = t2.storeId "
            + "AND t1.licenseType = t2.codeGroup "
            + "AND t1.licenseCode = t2.code "
            + "AND t2.delFlag = 0 "
            + "WHERE t1.storeId = :storeId "
            + "AND t1.licenseType = :licenseType "
            + "AND t1.licenseCode = :licenseCode "
            + "AND t1.delFlag = 0 ")
    PaymentMethodDto findStorePaymentByCode(@Param("storeId") String storeId,
        @Param("licenseType") String licenseType, @Param("licenseCode") String licenseCode);


    /**
     * 店舗言語情報取得.
     *
     * @param storeId     店舗ID
     * @param licenseType 種類CD
     * @return 言語情報
     */
    @Query(value =
        "SELECT new com.cnc.qr.core.pc.model.LanguageInfoDto(t1.licenseCode, t2.codeName) "
            + "FROM MLicense t1 "
            + "LEFT JOIN MCode t2 "
            + "ON t1.storeId = t2.storeId "
            + "AND t1.licenseType = t2.codeGroup "
            + "AND t1.licenseCode = t2.code "
            + "AND t2.delFlag = 0 "
            + "WHERE t1.storeId = :storeId "
            + "AND t1.licenseType = :licenseType "
            + "AND t1.delFlag = 0 "
            + "ORDER BY t1.licenseCode ASC")
    List<LanguageInfoDto> findStoreLanguageByStoreId(@Param("storeId") String storeId,
        @Param("licenseType") String licenseType);


    /**
     * ライセンスのコントロール取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return コントロール情報
     */
    List<MLicense> findByStoreIdAndDelFlag(String storeId, Integer delFlag);

}
