package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 権限リポジトリ.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
