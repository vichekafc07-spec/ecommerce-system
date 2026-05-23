package com.vichika.ecommercesystem.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vichika.ecommercesystem.audit.model.AuditLog;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class EntityAuditListener {

    private final ObjectMapper mapper = new ObjectMapper();

    @PostPersist
    public void onPostPersist(Object entity) {
        saveAudit(entity, "CREATE", null, entity);
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        saveAudit(entity, "UPDATE", null, entity);
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        saveAudit(entity, "DELETE", entity, null);
    }

    private void saveAudit(Object entity, String action, Object oldObj, Object newObj) {

        AuditLogRepository repo = SpringContext.getBean(AuditLogRepository.class);

        AuditLog log = new AuditLog();
        log.setEntityName(entity.getClass().getSimpleName());
        log.setAction(action);

        String name = extractName(entity);
        log.setEntityDisplayName(name);

        Long id = extractId(entity);
        log.setEntityId(id);

        log.setTimestamp(LocalDateTime.now());
        log.setUsername(getCurrentUser());

        if (oldObj != null)
            log.setOldValues(toJson(oldObj));

        if (newObj != null)
            log.setNewValues(toJson(newObj));

        repo.save(log);
    }

    private String extractName(Object entity) {
        try {
            Field nameField = entity.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            return (String) nameField.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    private Long extractId(Object entity) {
        try {
            Field id = entity.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Long) id.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (auth == null) ? "SYSTEM" : auth.getName();
        } catch (Exception e) {
            return "SYSTEM";
        }
    }

    private String toJson(Object object) {
        try {
            return mapper
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                    .writeValueAsString(object);

        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
