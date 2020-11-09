package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * メニューマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MMenuRepository extends JpaRepository<MMenu, Long> {

}
