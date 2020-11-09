package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MItemIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品索引マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MItemIndexRepository extends JpaRepository<MItemIndex, Long> {

}
