package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 支払テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OPaymentRepository extends JpaRepository<OPayment, Long> {

}
