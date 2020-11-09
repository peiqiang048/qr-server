package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ビジネスマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MBusinessRepository extends JpaRepository<MBusiness, Long> {

}
