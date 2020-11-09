package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PPaymentCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 支払会社情報テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface PPaymentCompanyRepository extends JpaRepository<PPaymentCompany, Long> {

    /**
     * 支払会社情報取得.
     *
     * @param storeId   店舗ID
     * @param delFlag   削除フラグ
     * @param paymentId 会社ID
     * @return 支払会社情報
     */
    PPaymentCompany findByStoreIdAndDelFlagAndCompanyId(String storeId, Integer delFlag,
        String paymentId);

    /**
     * 指定会社決済手段の支払会社情報取得.
     *
     * @param storeId       店舗ID
     * @param delFlag       削除フラグ
     * @param paymentId     会社ID
     * @param companyMethod 会社決済手段
     * @return 支払会社情報
     */
    PPaymentCompany findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(String storeId,
        Integer delFlag, String paymentId, String companyMethod);

}
