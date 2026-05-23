package com.vichika.ecommercesystem.audit;

import com.vichika.ecommercesystem.audit.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
