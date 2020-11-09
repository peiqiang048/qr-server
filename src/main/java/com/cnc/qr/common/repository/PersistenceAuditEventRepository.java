package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PersistentAuditEvent;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 永続性監査イベントリポジトリ.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(
        String principal, Instant after, String type);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate,
        Pageable pageable);

    List<PersistentAuditEvent> findByAuditEventDateBefore(Instant before);
}
