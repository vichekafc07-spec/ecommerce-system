package com.vichika.ecommercesystem.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vichika.ecommercesystem.audit.dto.CategoryAuditDto;
import com.vichika.ecommercesystem.audit.dto.ProductAuditDto;
import com.vichika.ecommercesystem.audit.dto.UserAuditDto;
import com.vichika.ecommercesystem.audit.model.AuditLog;
import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.category.Category;
import com.vichika.ecommercesystem.product.Product;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    @PreRemove
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
            log.setOldValues(toJson(convertToDto(oldObj)));

        if (newObj != null)
            log.setNewValues(toJson(convertToDto(newObj)));

        repo.save(log);
    }

    private Object convertToDto(Object entity) {

        if (entity instanceof AppUser user) {
            return new UserAuditDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
        }

        if (entity instanceof Category category) {
            return new CategoryAuditDto(
                    category.getId(),
                    category.getName(),
                    category.getCode()
            );
        }

        if (entity instanceof Product p) {
            return new ProductAuditDto(
                    p.getId(),
                    p.getName(),
                    p.getCode(),
                    p.getDescription(),
                    p.getQuantity(),
                    p.getPrice(),
                    p.getDiscount(),
                    p.getFinalPrice(),
                    p.getCategory() != null ? p.getCategory().getName() : null,
                    p.getCategory() != null ? p.getCategory().getId() : null
            );
        }

        return entity;
    }

    private String extractName(Object entity) {

        if (entity instanceof AppUser user) {
            return user.getUsername();
        }

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
            Method method = entity.getClass().getMethod("getId");
            Object value = method.invoke(entity);
            return value != null ? ((Number) value).longValue() : null;
        } catch (Exception e) {
            e.printStackTrace();
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
