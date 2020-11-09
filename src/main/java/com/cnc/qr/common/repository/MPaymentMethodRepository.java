package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 支払方法設定マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MPaymentMethodRepository extends JpaRepository<MPaymentMethod, Long> {

}
