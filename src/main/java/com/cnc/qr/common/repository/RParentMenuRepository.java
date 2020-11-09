package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RParentMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * メニュー関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RParentMenuRepository extends JpaRepository<RParentMenu, Long> {

}
