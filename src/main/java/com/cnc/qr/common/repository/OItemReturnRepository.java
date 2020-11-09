package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OItemReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 返品テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OItemReturnRepository extends JpaRepository<OItemReturn, Long> {

}
